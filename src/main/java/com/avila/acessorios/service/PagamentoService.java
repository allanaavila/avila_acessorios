package com.avila.acessorios.service;

import com.avila.acessorios.dto.PagamentoDTO;
import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import com.avila.acessorios.model.Pedido;
import com.avila.acessorios.repository.PagamentoRepository;
import com.avila.acessorios.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    public Pagamento criarPagamento(PagamentoDTO pagamentoDTO) {
        Pedido pedido = pedidoRepository.findById(pagamentoDTO.getIdPedido())
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + pagamentoDTO.getIdPedido() + " não encontrado."));

        if (pagamentoRepository.findByPedidoIdPedido(pedido.getIdPedido()) != null) {
            throw new IllegalStateException("Pagamento já cadastrado para o pedido de ID " + pedido.getIdPedido());
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(pagamentoDTO.getMetodoPagamento());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setTransacaoID(UUID.randomUUID().toString());

        return pagamentoRepository.save(pagamento);
    }


    public Pagamento buscarPagamentoPorPedido(Long idPedido) {
        return pagamentoRepository.findByPedidoIdPedido(idPedido);
    }


    public Pagamento atualizarStatusPagamento(Long idPagamento, StatusPagamento novoStatus) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento com ID " + idPagamento + " não encontrado."));

        pagamento.setStatusPagamento(novoStatus);
        return pagamentoRepository.save(pagamento);
    }


    public List<Pagamento> listarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento cancelarPagamento(Long idPagamento) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        pagamento.setStatusPagamento(StatusPagamento.CANCELADO);
        return pagamentoRepository.save(pagamento);
    }

}
