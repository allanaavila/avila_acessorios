package com.avila.acessorios.service;

import com.avila.acessorios.dto.ProdutoImagemDTO;
import com.avila.acessorios.model.Produto;
import com.avila.acessorios.model.ProdutoImagem;
import com.avila.acessorios.repository.ProdutoImagemRepository;
import com.avila.acessorios.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoImagemService {

    @Autowired
    private ProdutoImagemRepository produtoImagemRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoImagemDTO adicionarImagem(ProdutoImagemDTO dto) {
        Optional<Produto> produto = produtoRepository.findById(dto.getIdProduto());

        if (produto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado!");
        }

        ProdutoImagem produtoImagem = new ProdutoImagem();
        produtoImagem.setProduto(produto.get());
        produtoImagem.setImagemUrl(dto.getImagemUrl());

        ProdutoImagem imagemSalva = produtoImagemRepository.save(produtoImagem);
        return new ProdutoImagemDTO(imagemSalva);
    }

    public List<ProdutoImagemDTO> listarImagensPorProduto(Long idProduto) {
        return produtoImagemRepository.findByProdutoIdProduto(idProduto)
                .stream()
                .map(ProdutoImagemDTO::new)
                .collect(Collectors.toList());
    }

    public boolean deletarImagem(Long idImagem) {
        if (produtoImagemRepository.existsById(idImagem)) {
            produtoImagemRepository.deleteById(idImagem);
            return true;
        }
        return false;
    }

    public ProdutoImagemDTO atualizarImagem(Long idImagem, ProdutoImagemDTO dto) {
        Optional<ProdutoImagem> imagemExistente = produtoImagemRepository.findById(idImagem);

        if (imagemExistente.isPresent()) {
            ProdutoImagem produtoImagem = imagemExistente.get();
            produtoImagem.setImagemUrl(dto.getImagemUrl());

            ProdutoImagem imagemAtualizada = produtoImagemRepository.save(produtoImagem);
            return new ProdutoImagemDTO(imagemAtualizada);
        } else {
            throw new RuntimeException("Imagem não encontrada!");
        }
    }

}
