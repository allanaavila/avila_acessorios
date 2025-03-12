package com.avila.acessorios.repository;

import com.avila.acessorios.model.CarrinhoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long>{
    List<CarrinhoItem> findByCarrinhoIdCarrinho(Long idCarrinho);
}
