package br.com.fiap.pedidos.domain.model;

import br.com.fiap.pedidos.domain.enums.FormaPagamento;
import br.com.fiap.pedidos.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(nullable = false)
    private Long idCliente;

    @Column
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataPedido;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private BigDecimal totalPedido;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(nullable = false)
    @OneToMany
    private List<Produto> listaDeProdutos;

    @PrePersist
    protected void onCreate() {
        this.dataPedido = LocalDateTime.now();
        this.status = Status.NOVO;
    }

}
