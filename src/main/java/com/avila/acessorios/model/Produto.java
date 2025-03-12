package com.avila.acessorios.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produto", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
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


    @PrePersist
    protected void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }

    public Produto(String nome, String descricao, BigDecimal preco, String material, String categoria, Integer estoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.material = material;
        this.categoria = categoria;
        this.estoque = estoque;
    }
}
