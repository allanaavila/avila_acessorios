package com.avila.acessorios.repository;

import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Pagamento findByPedidoIdPedido(Long idPedido);
    List<Pagamento> findByStatusPagamento(StatusPagamento status);
}
