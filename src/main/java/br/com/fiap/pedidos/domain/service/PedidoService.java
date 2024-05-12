package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.config.MessageConfig;
import br.com.fiap.pedidos.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.model.Produto;
import br.com.fiap.pedidos.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ModelMapper modelMapper;

    private final MessageConfig messageConfig;

    private final SQSService sqsService;

    private void enviarEmailPedidoRecebido(Pedido pedido) {
//        MensagemEmail mensagemEmail = new MensagemEmail();
//        mensagemEmail.setEmailDestinatario(cliente.getEmail());
//        mensagemEmail.setAssunto(cliente.getNome() + ", Seja Bem-vindo ao FIAP e-commerce");
//        mensagemEmail.setCorpoEmail(cliente.getNome());
//
//        sqsService.enviarMensagem(mensagemEmail);
    }

    public PedidoDto add(PedidoDto pedidoDto) {
        var pedido = modelMapper.map(pedidoDto, Pedido.class);

        pedido.setTotalPedido(pedido.getListaDeProdutos().stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        pedido = pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoDto.class);
        //enviarEmailBoasVindas(pedido);
    }

    public PedidoDto getPedidoById(Long id) {
        var optionalPedido = pedidoRepository.findById(id);

        if(optionalPedido.isPresent()){
            return modelMapper.map(optionalPedido.get(), PedidoDto.class);
        } else {
            throw new PedidoNaoEncontradoException(messageConfig.getPedidoNaoEncontrado());
        }
    }

    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDto.class))
                .toList();
    }
}
