package com.avila.acessorios.controller;

import com.avila.acessorios.config.JwtUtil;
import com.avila.acessorios.dto.TokenDTO;
import com.avila.acessorios.dto.UsuarioCadastroDTO;
import com.avila.acessorios.dto.UsuarioDTO;
import com.avila.acessorios.dto.UsuarioLoginDTO;
import com.avila.acessorios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(@RequestBody UsuarioLoginDTO loginDTO) {
        UsuarioDTO usuario = usuarioService.autenticarUsuario(loginDTO);
        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getTipoUsuario());
        return ResponseEntity.ok(new TokenDTO(token));
    }


    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioCadastroDTO dto) {
        UsuarioDTO usuarioCriado = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioDTO> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioCadastroDTO dto) {
        Optional<UsuarioDTO> usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        return usuarioAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        boolean deletado = usuarioService.deletarUsuario(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
