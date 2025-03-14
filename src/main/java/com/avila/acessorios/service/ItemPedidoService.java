package com.avila.acessorios.service;

import com.avila.acessorios.dto.ItemPedidoDTO;
import com.avila.acessorios.model.ItemPedido;
import com.avila.acessorios.model.Pedido;
import com.avila.acessorios.model.Produto;
import com.avila.acessorios.repository.ItemPedidoRepository;
import com.avila.acessorios.repository.PedidoRepository;
import com.avila.acessorios.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;


    public ItemPedidoDTO criarItemPedido(Long idPedido, Long idProduto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        BigDecimal precoUnitario = produto.getPreco();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(precoUnitario);

        ItemPedido salvo = itemPedidoRepository.save(itemPedido);
        return new ItemPedidoDTO(salvo);
    }

    public List<ItemPedidoDTO> listarItensPorPedido(Long idPedido) {
        List<ItemPedido> itens = itemPedidoRepository.buscarItensPorPedido(idPedido);

        System.out.println("🔍 Buscando itens do pedido: " + idPedido);
        if (itens.isEmpty()) {
            System.out.println("⚠️ Nenhum item encontrado para o pedido " + idPedido);
            throw new RuntimeException("Nenhum item encontrado para o pedido " + idPedido);
        }

        for (ItemPedido item : itens) {
            System.out.println("✅ Item encontrado -> ID: " + item.getIdItemPedido() +
                    ", Pedido ID: " + item.getPedido().getIdPedido() +
                    ", Produto ID: " + item.getProduto().getIdProduto() +
                    ", Quantidade: " + item.getQuantidade());
        }

        return itens.stream()
                .map(ItemPedidoDTO::new)
                .collect(Collectors.toList());
    }

    public ItemPedidoDTO buscarItemPorId(Long idItemPedido) {
        System.out.println("🔍 Buscando item do pedido pelo ID: " + idItemPedido);

        ItemPedido itemPedido = itemPedidoRepository.findItemById(idItemPedido);
        if (itemPedido == null) {
            System.out.println("Item não encontrado.");
            throw new RuntimeException("Item não encontrado");
        }

        return new ItemPedidoDTO(itemPedido);
    }

    public ItemPedidoDTO atualizarItemPedido(Long idItemPedido, int novaQuantidade) {
        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ItemPedido itemPedido = itemPedidoRepository.findById(idItemPedido)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        itemPedido.setQuantidade(novaQuantidade);
        ItemPedido atualizado = itemPedidoRepository.save(itemPedido);

        return new ItemPedidoDTO(atualizado);
    }

    public void deletarItemPedido(Long idItemPedido) {
        ItemPedido itemPedido = itemPedidoRepository.findById(idItemPedido)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        itemPedidoRepository.delete(itemPedido);
    }

}
