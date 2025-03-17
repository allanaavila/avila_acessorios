package com.avila.acessorios.controller;

import com.avila.acessorios.dto.PagamentoDTO;
import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import com.avila.acessorios.service.GatewayPagamentoService;
import com.avila.acessorios.service.PagamentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private GatewayPagamentoService gatewayPagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@Valid @RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamento = pagamentoService.criarPagamento(pagamentoDTO);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping("/processar")
    public ResponseEntity<Map<String, String>> processarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("transacao_id", "fake-transacao-" + UUID.randomUUID());
        resposta.put("status", "CONFIRMADO");
        return ResponseEntity.ok(resposta);
    }


    @PostMapping("/webhook")
    public ResponseEntity<Void> receberWebhook(@RequestBody Map<String, Object> payload) {
        String transacaoID = (String) payload.get("transacao_id");
        String status = (String) payload.get("status");

        pagamentoService.processarWebhook(transacaoID, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<PagamentoDTO> buscarPagamentoPorPedido(@PathVariable Long idPedido) {
        Optional<Pagamento> pagamento = pagamentoService.buscarPagamentoPorPedido(idPedido);
        return pagamento.map(p -> ResponseEntity.ok(new PagamentoDTO(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{idPagamento}/status")
    public ResponseEntity<PagamentoDTO> atualizarStatusPagamento(
            @PathVariable Long idPagamento,
            @RequestParam StatusPagamento status) {
        Pagamento pagamento = pagamentoService.atualizarStatusPagamento(idPagamento, status);
        return ResponseEntity.ok(new PagamentoDTO(pagamento));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarTodosPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.listarTodosPagamentos();
        List<PagamentoDTO> pagamentosDTO = pagamentos.stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentosDTO);
    }

    @PutMapping("/{idPagamento}/cancelar")
    public ResponseEntity<PagamentoDTO> cancelarPagamento(@PathVariable Long idPagamento) {
        Pagamento pagamento = pagamentoService.cancelarPagamento(idPagamento);
        return ResponseEntity.ok(new PagamentoDTO(pagamento));
    }
}
