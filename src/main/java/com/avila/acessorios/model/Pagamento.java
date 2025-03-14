package com.avila.acessorios.model;

import com.avila.acessorios.model.Pagamentos.MetodoPagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "pagamento", schema = "public")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPagamento statusPagamento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataPagamento = LocalDateTime.now();

    @Column(unique = true, length = 255)
    private String transacaoID;

    public void setStatusPagamento(StatusPagamento status) {
        this.statusPagamento = status;
        if (status == StatusPagamento.APROVADO) {
            this.dataPagamento = LocalDateTime.now();
        }
    }


}

