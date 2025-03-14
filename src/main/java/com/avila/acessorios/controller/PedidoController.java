package com.avila.acessorios.controller;


import com.avila.acessorios.dto.PedidoDTO;
import com.avila.acessorios.model.StatusPedido;
import com.avila.acessorios.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/{idUsuario}/{idEndereco}")
    public ResponseEntity<PedidoDTO> criarPedido(
            @PathVariable Long idUsuario,
            @PathVariable Long idEndereco) {
        return ResponseEntity.ok(pedidoService.criarPedido(idUsuario, idEndereco));
    }



    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorUsuario(idUsuario));
    }

    @PutMapping("/{idPedido}/status")
    public ResponseEntity<PedidoDTO> atualizarStatusPedido(
            @PathVariable Long idPedido,
            @RequestParam StatusPedido status) {
        return ResponseEntity.ok(pedidoService.atualizarStatusPedido(idPedido, status));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long idPedido) {
        boolean deletado = pedidoService.deletarPedido(idPedido);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
