package com.avila.acessorios.dto;


import com.avila.acessorios.model.ItemPedido;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    private Long idItemPedido;
    private Long idPedido;
    private Long idProduto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemPedidoDTO(ItemPedido itemPedido) {
        this.idItemPedido = itemPedido.getIdItemPedido();
        this.idPedido = itemPedido.getPedido().getIdPedido();
        this.idProduto = itemPedido.getProduto().getIdProduto();
        this.quantidade = itemPedido.getQuantidade();
        this.precoUnitario = itemPedido.getPrecoUnitario();
    }
}
