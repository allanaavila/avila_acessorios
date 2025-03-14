package com.avila.acessorios.dto;

import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.MetodoPagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDTO {
    private Long idPedido;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;
    private String transacaoID;

    public PagamentoDTO(Pagamento pagamento) {
        this.idPedido = pagamento.getPedido().getIdPedido();
        this.metodoPagamento = pagamento.getMetodoPagamento();
        this.statusPagamento = pagamento.getStatusPagamento();
        this.transacaoID = pagamento.getTransacaoID();
    }
}
