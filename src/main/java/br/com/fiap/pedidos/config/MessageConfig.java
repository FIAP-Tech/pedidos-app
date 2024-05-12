package br.com.fiap.pedidos.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MessageConfig {

    @Value("${messages.error.pedido-nao-encontrado}")
    private String pedidoNaoEncontrado;

}
