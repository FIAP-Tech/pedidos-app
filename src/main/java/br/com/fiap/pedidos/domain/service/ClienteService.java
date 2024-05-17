package br.com.fiap.pedidos.domain.service;

import br.com.fiap.pedidos.api.client.ClienteClient;
import br.com.fiap.pedidos.api.model.ClienteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteClient client;

    public ClienteDto getClienteById(Long clienteId) {
        return client.getClienteById(clienteId).block();
    }
}

