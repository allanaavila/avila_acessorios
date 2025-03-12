package com.avila.acessorios.service;

import com.avila.acessorios.dto.EnderecoDTO;
import com.avila.acessorios.model.Endereco;
import com.avila.acessorios.model.TipoEndereco;
import com.avila.acessorios.model.Usuario;
import com.avila.acessorios.repository.EnderecoRepository;
import com.avila.acessorios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EnderecoDTO cadastrarEndereco(EnderecoDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findById(dto.getIdUsuario());

        if(usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setUf(dto.getUf());

        endereco.setTipoEndereco(TipoEndereco.valueOf(dto.getTipoEndereco()));

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return new EnderecoDTO(enderecoSalvo);
    }

    public List<EnderecoDTO> listarEnderecos() {
        return enderecoRepository.findAll()
                .stream()
                .map(EnderecoDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<EnderecoDTO> buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id).map(EnderecoDTO::new);
    }

    public boolean deletarEndereco(Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()) {
            enderecoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<EnderecoDTO> atualizarEndereco(Long id, EnderecoDTO dto) {
        Optional<Endereco> enderecoExistente = enderecoRepository.findById(id);

        if (enderecoExistente.isPresent()) {
            Endereco endereco = enderecoExistente.get();
            endereco.setCep(dto.getCep());
            endereco.setLogradouro(dto.getLogradouro());
            endereco.setNumero(dto.getNumero());
            endereco.setComplemento(dto.getComplemento());
            endereco.setBairro(dto.getBairro());
            endereco.setCidade(dto.getCidade());
            endereco.setUf(dto.getUf());

            endereco.setTipoEndereco(TipoEndereco.valueOf(dto.getTipoEndereco()));

            enderecoRepository.save(endereco);
            return Optional.of(new EnderecoDTO(endereco));
        }
        return Optional.empty();
    }




}
