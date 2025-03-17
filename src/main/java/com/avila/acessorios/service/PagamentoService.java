package com.avila.acessorios.service;

import com.avila.acessorios.dto.GatewayResponseDTO;
import com.avila.acessorios.dto.PagamentoDTO;
import com.avila.acessorios.exception.IllegalOperationException;
import com.avila.acessorios.exception.ResourceNotFoundException;
import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.MetodoPagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import com.avila.acessorios.model.Pedido;
import com.avila.acessorios.model.StatusPedido;
import com.avila.acessorios.repository.PagamentoRepository;
import com.avila.acessorios.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private GatewayPagamentoService gatewayPagamentoService;


    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Pedido pedido = pedidoRepository.findById(pagamentoDTO.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        if (pagamentoRepository.existsByPedidoIdPedido(pagamentoDTO.getIdPedido())) {
            throw new IllegalOperationException("Já existe um pagamento para este pedido.");
        }

        if (pedido.getStatusPedido() == StatusPedido.PAGO || pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new IllegalOperationException("O pedido já está pago ou cancelado.");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento(MetodoPagamento.valueOf(pagamentoDTO.getMetodoPagamento()));
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setDataPagamento(LocalDateTime.now());

        pagamento.setValorTotal(pedido.getTotalPedido());

        pagamentoDTO.setValorTotal(pedido.getTotalPedido());
        pagamentoDTO.setTransacaoID(UUID.randomUUID().toString());

        GatewayResponseDTO response = gatewayPagamentoService.processarPagamento(pagamentoDTO);

        pagamento.setTransacaoID(response.getTransacaoID());

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        return new PagamentoDTO(pagamentoSalvo);
    }




    public Optional<Pagamento> buscarPagamentoPorPedido(Long idPedido) {
        return pagamentoRepository.findByPedidoIdPedido(idPedido);
    }


    public Pagamento atualizarStatusPagamento(Long idPagamento, StatusPagamento status) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado."));
        pagamento.setStatusPagamento(status);
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento cancelarPagamento(Long idPagamento) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado."));
        pagamento.setStatusPagamento(StatusPagamento.CANCELADO);
        return pagamentoRepository.save(pagamento);
    }


    public void processarWebhook(String transacaoID, String statusGateway) {
        Pagamento pagamento = pagamentoRepository.findByTransacaoID(transacaoID)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada."));

        StatusPagamento novoStatus;
        switch (statusGateway.toUpperCase()) {
            case "PAID":
            case "COMPLETED":
                novoStatus = StatusPagamento.APROVADO;
                break;
            case "CANCELED":
                novoStatus = StatusPagamento.CANCELADO;
                break;
            case "FAILED":
                novoStatus = StatusPagamento.RECUSADO;
                break;
            default:
                novoStatus = StatusPagamento.PENDENTE;
        }

        pagamento.setStatusPagamento(novoStatus);
        pagamentoRepository.save(pagamento);

        Pedido pedido = pagamento.getPedido();
        if (novoStatus == StatusPagamento.APROVADO) {
            pedido.setStatusPedido(StatusPedido.PAGO);
        } else if (novoStatus == StatusPagamento.RECUSADO || novoStatus == StatusPagamento.CANCELADO) {
            pedido.setStatusPedido(StatusPedido.CANCELADO);
        }
        pedidoRepository.save(pedido);
    }
}
