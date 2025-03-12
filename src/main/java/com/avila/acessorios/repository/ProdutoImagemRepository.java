package com.avila.acessorios.repository;

import com.avila.acessorios.model.ProdutoImagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Long> {
    List<ProdutoImagem> findByProdutoIdProduto(Long idProduto);
}
