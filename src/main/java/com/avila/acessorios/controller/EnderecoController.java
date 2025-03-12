package com.avila.acessorios.controller;

import com.avila.acessorios.dto.EnderecoDTO;
import com.avila.acessorios.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<?> cadastrarEndereco(@RequestBody EnderecoDTO dto) {
        try {
            EnderecoDTO enderecoCriado = enderecoService.cadastrarEndereco(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar endereço: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarEnderecos() {
        return ResponseEntity.ok(enderecoService.listarEnderecos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorId(@PathVariable Long id) {
        EnderecoDTO enderecoDTO = enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.ok(enderecoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        boolean deletado = enderecoService.deletarEndereco(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO dto) {
        Optional<EnderecoDTO> enderecoAtualizado = enderecoService.atualizarEndereco(id, dto);
        return enderecoAtualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
