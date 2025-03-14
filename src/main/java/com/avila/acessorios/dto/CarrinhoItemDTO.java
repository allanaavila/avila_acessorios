package com.avila.acessorios.dto;

import com.avila.acessorios.model.CarrinhoItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrinhoItemDTO {
    private Long idItemCarrinho;
    private Long idCarrinho;
    private Long idProduto;
    private int quantidade;

    public CarrinhoItemDTO() {}

    public CarrinhoItemDTO(CarrinhoItem carrinhoItem) {
        this.idItemCarrinho = carrinhoItem.getIdItemCarrinho();
        this.idCarrinho = carrinhoItem.getCarrinho().getIdCarrinho();
        this.idProduto = carrinhoItem.getProduto().getIdProduto();
        this.quantidade = carrinhoItem.getQuantidade();
    }
}
