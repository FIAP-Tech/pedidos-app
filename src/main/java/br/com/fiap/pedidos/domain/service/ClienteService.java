package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.model.ClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class ClienteService {

    private final WebClient webClient;

    @Autowired
    public ClienteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api/clientes").build();
    }

    public ClienteDto getClienteById(Long clienteId) {
        return this.webClient.get()
                .uri("/{id}", clienteId)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(3))
                        .filter(WebClientResponseException.class::isInstance)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()))
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new RuntimeException("Erro ao se comunicar com a API de clientes: " + ex.getMessage())))
                .block();
    }
}

