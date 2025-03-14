package com.avila.acessorios.controller;


import com.avila.acessorios.dto.ItemPedidoDTO;
import com.avila.acessorios.service.ItemPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("item-pedido")
@RequiredArgsConstructor
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @PostMapping
    public ResponseEntity<?> criarItemPedido(@RequestBody ItemPedidoDTO itemPedidoDTO) {
        try {
            ItemPedidoDTO item = itemPedidoService.criarItemPedido(
                    itemPedidoDTO.getIdPedido(),
                    itemPedidoDTO.getIdProduto(),
                    itemPedidoDTO.getQuantidade()
            );
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Erro ao criar item do pedido.");
        }
    }


    @GetMapping("/{idPedido}")
    public ResponseEntity<List<ItemPedidoDTO>> listarItensPorPedido(@PathVariable Long idPedido) {
        try {
            List<ItemPedidoDTO> itens = itemPedidoService.listarItensPorPedido(idPedido);
            return ResponseEntity.ok(itens);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/item/{idItemPedido}")
    public ResponseEntity<ItemPedidoDTO> buscarItemPorId(@PathVariable Long idItemPedido) {
        ItemPedidoDTO item = itemPedidoService.buscarItemPorId(idItemPedido);
        return ResponseEntity.ok(item);
    }


    @PutMapping("/{idItemPedido}")
    public ResponseEntity<?> atualizarItemPedido(@PathVariable Long idItemPedido, @RequestBody ItemPedidoDTO itemPedidoDTO) {
        try {
            ItemPedidoDTO atualizado = itemPedidoService.atualizarItemPedido(idItemPedido, itemPedidoDTO.getQuantidade());
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idItemPedido}")
    public ResponseEntity<?> deletarItemPedido(@PathVariable Long idItemPedido) {
        try {
            itemPedidoService.deletarItemPedido(idItemPedido);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
