package com.avila.acessorios.controller;

import com.avila.acessorios.dto.ProdutoImagemDTO;
import com.avila.acessorios.service.ProdutoImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto-imagem")
public class ProdutoImagemController {

    @Autowired
    private ProdutoImagemService produtoImagemService;

    @PostMapping
    public ResponseEntity<ProdutoImagemDTO> adicionarImagem(@RequestBody ProdutoImagemDTO dto) {
        return ResponseEntity.ok(produtoImagemService.adicionarImagem(dto));
    }

    @GetMapping("/produto/{idProduto}")
    public ResponseEntity<List<ProdutoImagemDTO>> listarImagensPorProduto(@PathVariable Long idProduto) {
        return ResponseEntity.ok(produtoImagemService.listarImagensPorProduto(idProduto));
    }

    @DeleteMapping("/{idImagem}")
    public ResponseEntity<Void> deletarImagem(@PathVariable Long idImagem) {
        boolean deletado = produtoImagemService.deletarImagem(idImagem);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idImagem}")
    public ResponseEntity<ProdutoImagemDTO> atualizarImagem(
            @PathVariable Long idImagem,
            @RequestBody ProdutoImagemDTO dto) {
        return ResponseEntity.ok(produtoImagemService.atualizarImagem(idImagem, dto));
    }

}
