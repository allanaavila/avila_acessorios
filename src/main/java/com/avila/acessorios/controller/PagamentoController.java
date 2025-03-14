package com.avila.acessorios.controller;

import com.avila.acessorios.dto.PagamentoDTO;
import com.avila.acessorios.model.Pagamento;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import com.avila.acessorios.service.PagamentoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<?> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            return ResponseEntity.ok(pagamentoService.criarPagamento(pagamentoDTO));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar o pagamento.");
        }
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<?> buscarPagamentoPorPedido(@PathVariable Long idPedido) {
        try {
            return ResponseEntity.ok(pagamentoService.buscarPagamentoPorPedido(idPedido));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{idPagamento}/status")
    public ResponseEntity<?> atualizarStatusPagamento(
            @PathVariable Long idPagamento,
            @RequestParam StatusPagamento status) {
        try {
            return ResponseEntity.ok(pagamentoService.atualizarStatusPagamento(idPagamento, status));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar status do pagamento.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> listarTodosPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarTodosPagamentos());
    }


    @PutMapping("/{idPagamento}/cancelar")
    public ResponseEntity<Pagamento> cancelarPagamento(@PathVariable Long idPagamento) {
        return ResponseEntity.ok(pagamentoService.cancelarPagamento(idPagamento));
    }

}
