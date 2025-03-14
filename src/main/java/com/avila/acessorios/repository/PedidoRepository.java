package com.avila.acessorios.repository;


import com.avila.acessorios.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdUsuario(Long idUsuario);
}
