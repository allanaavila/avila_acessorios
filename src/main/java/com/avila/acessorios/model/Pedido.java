package com.avila.acessorios.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido", schema = "public")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco enderecoEntrega;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido statusPedido = StatusPedido.PENDENTE;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPedido;

}
