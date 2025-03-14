package com.avila.acessorios.repository;

import com.avila.acessorios.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedidoIdPedido(Long idPedido);
    @Query("SELECT i FROM ItemPedido i WHERE i.pedido.idPedido = :idPedido")
    List<ItemPedido> buscarItensPorPedido(@Param("idPedido") Long idPedido);

    @Query("SELECT i FROM ItemPedido i WHERE i.idItemPedido = :idItemPedido")
    ItemPedido findItemById(@Param("idItemPedido") Long idItemPedido);

}
