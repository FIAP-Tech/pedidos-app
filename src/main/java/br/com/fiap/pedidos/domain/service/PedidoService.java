package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.exceptionhandler.ClienteNaoEncontradoException;
import br.com.fiap.pedidos.api.model.ClienteDto;
import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.api.model.PedidoProcessaDto;
import br.com.fiap.pedidos.config.MessageConfig;
import br.com.fiap.pedidos.domain.enums.Status;
import br.com.fiap.pedidos.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.pedidos.api.model.MensagemEmailDto;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ModelMapper modelMapper;

    private final MessageConfig messageConfig;

    private final SQSService sqsService;

    private final ClienteService clienteService;

    private void enviarEmailPedidoRecebido(Pedido pedido, ClienteDto cliente) {
        var mensagemEmailDto = new MensagemEmailDto();
        mensagemEmailDto.setEmailDestinatario(cliente.getEmail());
        mensagemEmailDto.setAssunto("Pedido recebido com Sucesso");
        mensagemEmailDto.criarCorpoEmailPedido(pedido, cliente.getNome());

        sqsService.enviarMensagemFilaEmail(mensagemEmailDto);
    }

    private void enviarPedidoParaProcessamento(Pedido pedido, ClienteDto cliente) {
        var pedidoProcessaDto = new PedidoProcessaDto();
        modelMapper.map(pedido, pedidoProcessaDto);
        modelMapper.map(cliente, pedidoProcessaDto);

        sqsService.enviarMensagemFilaPedidos(pedidoProcessaDto);
    }

    private void atualizarStatus(Pedido pedido) {
        pedido.setStatus(Status.VERIFICANDO_PAGAMENTO);
        pedidoRepository.save(pedido);
    }

    private void processarPedido(Pedido pedido, ClienteDto cliente) {
        enviarEmailPedidoRecebido(pedido, cliente);
        enviarPedidoParaProcessamento(pedido, cliente);
        atualizarStatus(pedido);
    }

    public PedidoDto add(PedidoDto pedidoDto) {
        var pedido = modelMapper.map(pedidoDto, Pedido.class);

        ClienteDto clienteDto = clienteService.getClienteById(pedido.getIdCliente());

        if(clienteDto == null) {
            throw new ClienteNaoEncontradoException(messageConfig.getClienteNaoEncontrado());
        }

        pedido = pedidoRepository.save(pedido);
        processarPedido(pedido, clienteDto);

        return modelMapper.map(pedido, PedidoDto.class);
    }

    @Transactional(readOnly = true)
    public PedidoDto getPedidoById(Long id) {
        var optionalPedido = pedidoRepository.findById(id);

        if(optionalPedido.isPresent()){
            return modelMapper.map(optionalPedido.get(), PedidoDto.class);
        } else {
            throw new PedidoNaoEncontradoException(messageConfig.getPedidoNaoEncontrado());
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDto.class))
                .toList();
    }

    public Optional<Pedido> atualizaStatusPedido(Long orderId, Status novoStatus) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(orderId);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);
            return Optional.of(pedido);
        }
        return Optional.empty();
    }
}
