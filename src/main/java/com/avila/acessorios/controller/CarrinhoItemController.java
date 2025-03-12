package com.avila.acessorios.controller;

import com.avila.acessorios.dto.CarrinhoItemDTO;
import com.avila.acessorios.service.CarrinhoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carrinho-item")
public class CarrinhoItemController {


    @Autowired
    private CarrinhoItemService carrinhoItemService;

    @PostMapping
    public ResponseEntity<CarrinhoItemDTO> adicionarItem(@RequestBody CarrinhoItemDTO dto) {
        return ResponseEntity.ok(carrinhoItemService.adicionarItemAoCarrinho(dto));
    }

    @GetMapping("/carrinho/{idCarrinho}")
    public ResponseEntity<List<CarrinhoItemDTO>> listarItens(@PathVariable Long idCarrinho) {
        return ResponseEntity.ok(carrinhoItemService.listarItensDoCarrinho(idCarrinho));
    }

    @PutMapping("/{idItemCarrinho}")
    public ResponseEntity<CarrinhoItemDTO> atualizarQuantidade(
            @PathVariable Long idItemCarrinho,
            @RequestBody CarrinhoItemDTO dto) {
        return ResponseEntity.ok(carrinhoItemService.atualizarQuantidade(idItemCarrinho, dto));
    }

    @DeleteMapping("/{idItemCarrinho}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long idItemCarrinho) {
        boolean deletado = carrinhoItemService.deletarItemDoCarrinho(idItemCarrinho);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
