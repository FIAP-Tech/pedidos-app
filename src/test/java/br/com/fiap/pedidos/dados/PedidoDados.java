package br.com.fiap.pedidos.dados;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.model.Produto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PedidoDados {

    List<Produto> produtos = Arrays.asList(new Produto(1L, BigDecimal.valueOf(13.00), 10),
                                            new Produto(2L, BigDecimal.valueOf(15.00), 5),
                                            new Produto(3L, BigDecimal.valueOf(25.00), 1));

    public Pedido criarPedido1(){
        return Pedido.builder()
                .idPedido(1L)
                .idCliente(1L)
                .listaDeProdutos(produtos)
                .build();
    }

    public Pedido criarPedido2(){
        return Pedido.builder()
                .idPedido(1L)
                .idCliente(1L)
                .listaDeProdutos(produtos)
                .build();
    }

    public PedidoDto criarPedidoDto1(){
        return PedidoDto.builder()
                .idPedido(1L)
                .idCliente(1L)
                .listaDeProdutos(produtos)
                .build();
    }

    public PedidoDto criarPedidoDto2(){
        return PedidoDto.builder()
                .idPedido(1L)
                .idCliente(1L)
                .listaDeProdutos(produtos)
                .build();
    }
}
