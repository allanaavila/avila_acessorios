package com.avila.acessorios.repository;

import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByPedidoIdPedido(Long idPedido);
    Optional<Pagamento> findByTransacaoID(String transacaoID);
    List<Pagamento> findByStatusPagamento(StatusPagamento status);
    boolean existsByPedidoIdPedido(Long idPedido);
}
