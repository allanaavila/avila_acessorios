package com.avila.acessorios.service;


import com.avila.acessorios.dto.UsuarioCadastroDTO;
import com.avila.acessorios.dto.UsuarioDTO;
import com.avila.acessorios.dto.UsuarioLoginDTO;
import com.avila.acessorios.exception.EmailJaCadastradoException;
import com.avila.acessorios.model.TipoUsuario;
import com.avila.acessorios.model.Usuario;
import com.avila.acessorios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO cadastrarUsuario(UsuarioCadastroDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException("E-mail já cadastrado!");
        }

        if (usuarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado!");
        }

        Usuario usuario = new Usuario(
                dto.getNome(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getSenha()),
                dto.getCpf(),
                dto.getTelefone(),
                dto.getDataNascimento()
        );

        usuario.setTipoUsuario(TipoUsuario.USER);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return toDTO(usuarioSalvo);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::toDTO);
    }

    public UsuarioDTO autenticarUsuario(UsuarioLoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        System.out.println("Senha digitada: " + loginDTO.getSenha());
        System.out.println("Senha no banco: " + usuario.getSenha());
        System.out.println("Comparação: " + passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha()));

        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }

        return toDTO(usuario);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCpf(usuario.getCpf());
        dto.setTelefone(usuario.getTelefone());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setTipoUsuario(usuario.getTipoUsuario().name());
        return dto;
    }



}
