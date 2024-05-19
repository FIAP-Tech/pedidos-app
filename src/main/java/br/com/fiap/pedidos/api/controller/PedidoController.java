package br.com.fiap.pedidos.api.controller;

import br.com.fiap.pedidos.api.model.PedidoDto;
import br.com.fiap.pedidos.domain.enums.Status;
import br.com.fiap.pedidos.domain.service.PedidoService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    public List<PedidoDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PedidoDto getPedidoById(@PathVariable Long id) {
        return service.getPedidoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto add(@RequestBody @NotNull PedidoDto pedidoDto) {
        return service.add(pedidoDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> atualizaStatusPedido(@PathVariable Long id, @RequestParam Status status) {
        return service.atualizaStatusPedido(id, status)
                .map(order -> ResponseEntity.ok("Pedido atualizado para o status: " + status))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
