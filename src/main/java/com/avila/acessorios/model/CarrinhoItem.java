package com.avila.acessorios.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "carrinho_item")
public class CarrinhoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_carrinho")
    private Long idItemCarrinho;

    @ManyToOne
    @JoinColumn(name = "id_carrinho", nullable = false, foreignKey = @ForeignKey(name = "fk_carrinho_item"))
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_item"))
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;
}
