package com.avila.acessorios.dto;

import com.avila.acessorios.model.Pagamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    private Long idPagamento;
    private Long idPedido;
    private BigDecimal valorTotal;
    private String metodoPagamento;
    private String statusPagamento;
    private String transacaoID;
    private LocalDateTime dataPagamento;

    public PagamentoDTO(Pagamento pagamento) {
        this.idPagamento = pagamento.getIdPagamento();
        this.idPedido = pagamento.getPedido().getIdPedido();
        this.valorTotal = pagamento.getValorTotal();
        this.metodoPagamento = pagamento.getMetodoPagamento().name();
        this.statusPagamento = pagamento.getStatusPagamento().toString();
        this.transacaoID = pagamento.getTransacaoID();
        this.dataPagamento = pagamento.getDataPagamento();

    }
}
