package com.avila.acessorios.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "produto_imagem")
public class ProdutoImagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagem")
    private Long idImagem;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_imagem"))
    private Produto produto;

    @Column(name = "imagem_url", nullable = false, columnDefinition = "TEXT")
    private String imagemUrl;
}
