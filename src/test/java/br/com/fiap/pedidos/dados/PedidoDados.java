package br.com.fiap.pedidos.dados;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.api.model.PedidoProdutoDto;
import br.com.fiap.pedidos.domain.model.Pedido;
import br.com.fiap.pedidos.domain.model.PedidoProduto;

import java.util.ArrayList;
import java.util.List;

public class PedidoDados {

    List<PedidoProduto> pedidoProdutos = new ArrayList<>();
    List<PedidoProdutoDto> pedidoProdutosDto = new ArrayList<>();


    public Pedido criarPedido1(){
        return Pedido.builder()
                .idPedido(1L)
                .idCliente(1L)
                .pedidoProdutos(pedidoProdutos)
                .build();
    }

    public Pedido criarPedido2(){
        return Pedido.builder()
                .idPedido(1L)
                .idCliente(1L)
                .pedidoProdutos(pedidoProdutos)
                .build();
    }

    public PedidoDto criarPedidoDto1(){
        return PedidoDto.builder()
                .idPedido(1L)
                .idCliente(1L)
                .pedidoProdutos(pedidoProdutosDto)
                .build();
    }

    public PedidoDto criarPedidoDto2(){
        return PedidoDto.builder()
                .idPedido(1L)
                .idCliente(1L)
                .pedidoProdutos(pedidoProdutosDto)
                .build();
    }
}
