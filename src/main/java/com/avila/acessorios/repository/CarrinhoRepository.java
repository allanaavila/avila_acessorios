package com.avila.acessorios.repository;

import com.avila.acessorios.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    List<Carrinho> findByUsuarioIdUsuario(Long idUsuario);
}