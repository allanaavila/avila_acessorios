package com.avila.acessorios.controller;

import com.avila.acessorios.dto.CarrinhoDTO;
import com.avila.acessorios.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;


    @PostMapping("/{idUsuario}")
    public ResponseEntity<CarrinhoDTO> criarCarrinho(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carrinhoService.criarCarrinho(idUsuario));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<CarrinhoDTO>> listarCarrinhosPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carrinhoService.listarCarrinhosPorUsuario(idUsuario));
    }

    @DeleteMapping("/{idCarrinho}")
    public ResponseEntity<Void> deletarCarrinho(@PathVariable Long idCarrinho) {
        boolean deletado = carrinhoService.deletarCarrinho(idCarrinho);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
