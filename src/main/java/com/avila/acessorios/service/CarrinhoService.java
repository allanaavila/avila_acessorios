package com.avila.acessorios.service;

import com.avila.acessorios.dto.CarrinhoDTO;
import com.avila.acessorios.model.Carrinho;
import com.avila.acessorios.model.Usuario;
import com.avila.acessorios.repository.CarrinhoRepository;
import com.avila.acessorios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CarrinhoDTO criarCarrinho(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario.get());

        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);
        return new CarrinhoDTO(carrinhoSalvo);
    }


    public List<CarrinhoDTO> listarCarrinhosPorUsuario(Long idUsuario) {
        return carrinhoRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(CarrinhoDTO::new)
                .collect(Collectors.toList());
    }

    public boolean deletarCarrinho(Long idCarrinho) {
        if (carrinhoRepository.existsById(idCarrinho)) {
            carrinhoRepository.deleteById(idCarrinho);
            return true;
        }
        return false;
    }

}
