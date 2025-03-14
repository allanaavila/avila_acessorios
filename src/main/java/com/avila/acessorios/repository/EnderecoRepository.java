package com.avila.acessorios.repository;

import com.avila.acessorios.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByUsuarioIdUsuario(Long idUsuario);

    @Query("SELECT e FROM Endereco e JOIN FETCH e.usuario WHERE e.idEndereco = :idEndereco")
    Optional<Endereco> findByIdWithUsuario(@Param("idEndereco") Long idEndereco);
}
