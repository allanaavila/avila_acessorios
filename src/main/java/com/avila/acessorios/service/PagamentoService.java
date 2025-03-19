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
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private AuditoriaService auditoriaService;


    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Pedido pedido = pedidoRepository.findById(pagamentoDTO.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));

        Optional<Pagamento> pagamentoExistente = pagamentoRepository.findByPedidoIdPedido(pagamentoDTO.getIdPedido());
        if (pagamentoExistente.isPresent()) {
            if (pagamentoExistente.get().getStatusPagamento() == StatusPagamento.APROVADO) {
                throw new IllegalOperationException("O pedido já está pago.");
            }
            if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
                throw new IllegalOperationException("O pedido foi cancelado.");
            }
            throw new IllegalOperationException("Já existe um pagamento pendente ou recusado para este pedido.");
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

        String emailUsuario = auditoriaService.obterUsuarioAutenticado();

        auditoriaService.registrar(
                "Pagamento",
                "Criação",
                "Pagamento criado para Pedido ID " + pedido.getIdPedido() +
                        ", Método: " + pagamento.getMetodoPagamento() +
                        ", Transação ID: " + pagamento.getTransacaoID() +
                        ", Valor: " + pagamento.getValorTotal(),
                emailUsuario
        );


        return new PagamentoDTO(pagamentoSalvo);
    }




    public Optional<Pagamento> buscarPagamentoPorPedido(Long idPedido) {
        return pagamentoRepository.findByPedidoIdPedido(idPedido);
    }


    public Pagamento atualizarStatusPagamento(Long idPagamento, StatusPagamento status) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado."));

        pagamento.setStatusPagamento(status);
        Pagamento atualizado = pagamentoRepository.save(pagamento);


        String emailUsuario = auditoriaService.obterUsuarioAutenticado();

        auditoriaService.registrar(
                "Pagamento",
                "Atualização de Status",
                "Status do pagamento ID " + idPagamento + " atualizado para " + status,
                emailUsuario
        );

        return atualizado;
    }

    public List<Pagamento> listarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    public Pagamento cancelarPagamento(Long idPagamento) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado."));

        pagamento.setStatusPagamento(StatusPagamento.CANCELADO);
        Pagamento cancelado = pagamentoRepository.save(pagamento);

        String emailUsuario = auditoriaService.obterUsuarioAutenticado();

        auditoriaService.registrar(
                "Pagamento",
                "Cancelamento",
                "Pagamento ID " + idPagamento + " foi cancelado.",
                emailUsuario
        );

        return cancelado;
    }


    public void processarWebhook(String transacaoID, String statusGateway) {
        Pagamento pagamento = pagamentoRepository.findByTransacaoID(transacaoID)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada."));

        StatusPagamento novoStatus;
        switch (statusGateway.toUpperCase()) {
            case "PAGO":
            case "COMPLETO":
                novoStatus = StatusPagamento.APROVADO;
                break;
            case "CANCELADO":
                novoStatus = StatusPagamento.CANCELADO;
                break;
            case "FALHOU":
            case "RECUSADO":
                novoStatus = StatusPagamento.RECUSADO;
                break;
            case "REEMBOLSADO":
                novoStatus = StatusPagamento.REEMBOLSADO;
                break;
            case "ESTORNADO":
                novoStatus = StatusPagamento.ESTORNADO;
                break;
            default:
                novoStatus = StatusPagamento.PENDENTE;
        }

        pagamento.setStatusPagamento(novoStatus);
        pagamentoRepository.save(pagamento);

        Pedido pedido = pagamento.getPedido();

        if (novoStatus == StatusPagamento.RECUSADO || novoStatus == StatusPagamento.CANCELADO ||
                novoStatus == StatusPagamento.ESTORNADO || novoStatus == StatusPagamento.REEMBOLSADO) {
            pedido.setStatusPedido(StatusPedido.CANCELADO);
        }

        pedidoRepository.save(pedido);

        auditoriaService.registrar(
                "Pagamento",
                "Webhook Processado",
                "Webhook processado: Transação ID " + transacaoID +
                        ", Novo status: " + novoStatus +
                        ", Pedido ID: " + pedido.getIdPedido(),
                "Gateway"
        );
    }

}
