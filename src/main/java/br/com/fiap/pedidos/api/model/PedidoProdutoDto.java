package br.com.fiap.pedidos.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoProdutoDto {

    private Long idProduto;

    private Integer quantidade;

    private BigDecimal preco;
}

