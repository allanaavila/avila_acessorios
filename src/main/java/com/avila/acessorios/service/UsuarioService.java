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

        usuario.setTipoUsuario(dto.getTipoUsuario());

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

        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }

        return toDTO(usuario);
    }

    public Optional<UsuarioDTO> atualizarUsuario(Long id, UsuarioCadastroDTO dto) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();

            Optional<Usuario> usuarioComMesmoEmail = usuarioRepository.findByEmail(dto.getEmail());
            if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getIdUsuario().equals(id)) {
                throw new RuntimeException("E-mail já cadastrado por outro usuário!");
            }

            Optional<Usuario> usuarioComMesmoCpf = usuarioRepository.findByCpf(dto.getCpf());
            if (usuarioComMesmoCpf.isPresent() && !usuarioComMesmoCpf.get().getIdUsuario().equals(id)) {
                throw new RuntimeException("CPF já cadastrado por outro usuário!");
            }

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefone(dto.getTelefone());
            usuario.setCpf(dto.getCpf());
            usuario.setDataNascimento(dto.getDataNascimento());

            if (dto.getTipoUsuario() != null) {
                usuario.setTipoUsuario(dto.getTipoUsuario());
            }

            usuarioRepository.save(usuario);
            return Optional.of(toDTO(usuario));
        }

        return Optional.empty();
    }


    public boolean deletarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCpf(usuario.getCpf());
        dto.setTelefone(usuario.getTelefone());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setTipoUsuario(usuario.getTipoUsuario().name());
        return dto;
    }

}
