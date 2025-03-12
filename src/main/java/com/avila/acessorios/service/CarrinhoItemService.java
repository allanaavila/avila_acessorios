package com.avila.acessorios.service;

import com.avila.acessorios.dto.CarrinhoItemDTO;
import com.avila.acessorios.model.Carrinho;
import com.avila.acessorios.model.CarrinhoItem;
import com.avila.acessorios.model.Produto;
import com.avila.acessorios.repository.CarrinhoItemRepository;
import com.avila.acessorios.repository.CarrinhoRepository;
import com.avila.acessorios.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrinhoItemService {

    @Autowired
    private CarrinhoItemRepository carrinhoItemRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public CarrinhoItemDTO adicionarItemAoCarrinho(CarrinhoItemDTO dto) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(dto.getIdCarrinho());
        Optional<Produto> produto = produtoRepository.findById(dto.getIdProduto());

        if (carrinho.isEmpty()) {
            throw new RuntimeException("Carrinho não encontrado!");
        }

        if (produto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado!");
        }

        CarrinhoItem carrinhoItem = new CarrinhoItem();
        carrinhoItem.setCarrinho(carrinho.get());
        carrinhoItem.setProduto(produto.get());
        carrinhoItem.setQuantidade(dto.getQuantidade());

        CarrinhoItem itemSalvo = carrinhoItemRepository.save(carrinhoItem);
        return new CarrinhoItemDTO(itemSalvo);
    }


    public List<CarrinhoItemDTO> listarItensDoCarrinho(Long idCarrinho) {
        return carrinhoItemRepository.findByCarrinhoIdCarrinho(idCarrinho)
                .stream()
                .map(CarrinhoItemDTO::new)
                .collect(Collectors.toList());
    }

    public boolean deletarItemDoCarrinho(Long idItemCarrinho) {
        if (carrinhoItemRepository.existsById(idItemCarrinho)) {
            carrinhoItemRepository.deleteById(idItemCarrinho);
            return true;
        }
        return false;
    }

    public CarrinhoItemDTO atualizarQuantidade(Long idItemCarrinho, CarrinhoItemDTO dto) {
        Optional<CarrinhoItem> itemExistente = carrinhoItemRepository.findById(idItemCarrinho);

        if (itemExistente.isPresent()) {
            CarrinhoItem carrinhoItem = itemExistente.get();
            carrinhoItem.setQuantidade(dto.getQuantidade());

            CarrinhoItem itemAtualizado = carrinhoItemRepository.save(carrinhoItem);
            return new CarrinhoItemDTO(itemAtualizado);
        } else {
            throw new RuntimeException("Item do carrinho não encontrado!");
        }
    }


}
