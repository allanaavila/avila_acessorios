package com.avila.acessorios.model;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produto", schema = "public")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(length = 100)
    private String material;

    @Column(length = 100)
    private String categoria;

    @Column(nullable = false)
    private Integer estoque;

    @Column(name = "dataCadastro", updatable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();
}
