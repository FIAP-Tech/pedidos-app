package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.model.ClienteDto;
import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.config.MessageConfig;
import br.com.fiap.pedidos.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.pedidos.domain.model.MensagemEmail;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ModelMapper modelMapper;

    private final MessageConfig messageConfig;

    private final SQSService sqsService;

    private final ClienteService clienteService;

    private void enviarEmailPedidoRecebido(Pedido pedido, ClienteDto cliente) {
        MensagemEmail mensagemEmail = new MensagemEmail();
        mensagemEmail.setEmailDestinatario(cliente.getEmail());
        mensagemEmail.setAssunto(cliente.getNome() + ", Seja Bem-vindo ao FIAP e-commerce");
        mensagemEmail.criarCorpoEmailPedido(pedido, cliente.getNome());

        sqsService.enviarMensagem(mensagemEmail);
    }

    public PedidoDto add(PedidoDto pedidoDto) {
        var pedido = modelMapper.map(pedidoDto, Pedido.class);

        ClienteDto clienteDto = clienteService.getClienteById(pedido.getIdCliente());

        pedido = pedidoRepository.save(pedido);
        enviarEmailPedidoRecebido(pedido, clienteDto);

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
}
