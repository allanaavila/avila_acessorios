package com.avila.acessorios.dto;

import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import com.avila.acessorios.model.StatusPedido;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalhadoDTO {
    private Long idPedido;
    private String nomeUsuario;
    private String cpfUsuario;
    private List<String> produtos;
    private BigDecimal totalPedido;
    private StatusPedido statusPedido;
    private StatusPagamento statusPagamento;


}
