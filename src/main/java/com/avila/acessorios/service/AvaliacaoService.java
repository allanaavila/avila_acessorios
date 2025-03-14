package com.avila.acessorios.service;

import com.avila.acessorios.dto.AvaliacaoDTO;
import com.avila.acessorios.model.*;
import com.avila.acessorios.repository.AvaliacaoRepository;
import com.avila.acessorios.repository.PedidoRepository;
import com.avila.acessorios.repository.ProdutoRepository;
import com.avila.acessorios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;


    public AvaliacaoDTO avaliarProduto(Long idUsuario, Long idProduto, int nota, String comentario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findByUsuarioIdUsuarioAndProdutoIdProduto(idUsuario, idProduto);
        if (avaliacaoExistente.isPresent()) {
            throw new RuntimeException("Usuário já avaliou este produto!");
        }

        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdUsuario(idUsuario);
        boolean podeAvaliar = pedidos.stream()
                .filter(pedido -> !pedido.getStatusPedido().equals(StatusPedido.PENDENTE))
                .flatMap(pedido -> pedido.getItens().stream())
                .anyMatch(item -> item.getProduto().getIdProduto().equals(idProduto)); 

        if (!podeAvaliar) {
            throw new RuntimeException("O usuário só pode avaliar produtos que ele já comprou e cujo pedido não está pendente.");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUsuario(usuario);
        avaliacao.setProduto(produto);
        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario);

        return new AvaliacaoDTO(avaliacaoRepository.save(avaliacao));
    }


    public List<AvaliacaoDTO> listarAvaliacoesPorProduto(Long idProduto) {
        return avaliacaoRepository.findByProdutoIdProduto(idProduto)
                .stream()
                .map(AvaliacaoDTO::new)
                .collect(Collectors.toList());
    }
}
