package com.avila.acessorios.controller;


import com.avila.acessorios.dto.AvaliacaoDTO;
import com.avila.acessorios.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;


    @PostMapping
    public ResponseEntity<AvaliacaoDTO> avaliarProduto(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        return ResponseEntity.ok(avaliacaoService.avaliarProduto(
                avaliacaoDTO.getIdUsuario(),
                avaliacaoDTO.getIdProduto(),
                avaliacaoDTO.getNota(),
                avaliacaoDTO.getComentario()
        ));
    }

    @GetMapping("/produto/{idProduto}")
    public ResponseEntity<List<AvaliacaoDTO>> listarAvaliacoesPorProduto(@PathVariable Long idProduto) {
        return ResponseEntity.ok(avaliacaoService.listarAvaliacoesPorProduto(idProduto));
    }
}
