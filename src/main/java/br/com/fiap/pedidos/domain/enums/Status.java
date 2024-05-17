package br.com.fiap.pedidos.domain.enums;


public enum Status {

    NOVO,
    VERIFICANDO_PAGAMENTO,
    AGUARDANDO_RETIRADA,
    EM_ROTA_DE_ENTREGA,
    FALHA_NA_ENTREGA,
    ENTREGUE;

}
