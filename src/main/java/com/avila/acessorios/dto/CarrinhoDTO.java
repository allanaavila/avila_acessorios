package com.avila.acessorios.dto;

import com.avila.acessorios.model.Carrinho;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CarrinhoDTO {
    private Long idCarrinho;
    private Long idUsuario;
    private LocalDateTime dataCriacao;

    public CarrinhoDTO() {}

    public CarrinhoDTO(Carrinho carrinho) {
        this.idCarrinho = carrinho.getIdCarrinho();
        this.idUsuario = carrinho.getUsuario().getIdUsuario();
        this.dataCriacao = carrinho.getDataCriacao();
    }
}
