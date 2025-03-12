package com.avila.acessorios.dto;

import com.avila.acessorios.model.ProdutoImagem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoImagemDTO {
    private Long idImagem;
    private Long idProduto;
    private String imagemUrl;

    public ProdutoImagemDTO() {}

    public ProdutoImagemDTO(ProdutoImagem produtoImagem) {
        this.idImagem = produtoImagem.getIdImagem();
        this.idProduto = produtoImagem.getProduto().getIdProduto();
        this.imagemUrl = produtoImagem.getImagemUrl();
    }
}
