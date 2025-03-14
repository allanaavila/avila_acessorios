package com.avila.acessorios.repository;

import com.avila.acessorios.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findByUsuarioIdUsuarioAndProdutoIdProduto(Long idUsuario, Long idProduto);
    List<Avaliacao> findByProdutoIdProduto(Long idProduto);
}
