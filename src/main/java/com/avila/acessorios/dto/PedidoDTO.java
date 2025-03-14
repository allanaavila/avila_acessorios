package com.avila.acessorios.dto;

import com.avila.acessorios.model.Pedido;
import com.avila.acessorios.model.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long idPedido;
    private Long idUsuario;
    private Long idEnderecoEntrega;
    private LocalDateTime dataPedido;
    private StatusPedido statusPedido;
    private BigDecimal totalPedido;

    public PedidoDTO(Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.idUsuario = pedido.getUsuario().getIdUsuario();
        this.idEnderecoEntrega = pedido.getEnderecoEntrega().getIdEndereco();
        this.dataPedido = pedido.getDataPedido();
        this.statusPedido = pedido.getStatusPedido();
        this.totalPedido = pedido.getTotalPedido();
    }
}
