package com.avila.acessorios.service;

import com.avila.acessorios.dto.PedidoDTO;
import com.avila.acessorios.model.Endereco;
import com.avila.acessorios.model.Pedido;
import com.avila.acessorios.model.StatusPedido;
import com.avila.acessorios.model.Usuario;
import com.avila.acessorios.repository.EnderecoRepository;
import com.avila.acessorios.repository.PedidoRepository;
import com.avila.acessorios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public PedidoDTO criarPedido(Long idUsuario, Long idEnderecoEntrega, BigDecimal totalPedido) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        Optional<Endereco> endereco = enderecoRepository.findById(idEnderecoEntrega);

        if (usuario.isEmpty() || endereco.isEmpty()) {
            throw new RuntimeException("Usuário ou endereço não encontrado!");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario.get());
        pedido.setEnderecoEntrega(endereco.get());
        pedido.setTotalPedido(totalPedido);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new PedidoDTO(pedidoSalvo);
    }


    public List<PedidoDTO> listarPedidosPorUsuario(Long idUsuario) {
        return pedidoRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }


    public PedidoDTO atualizarStatusPedido(Long idPedido, StatusPedido novoStatus) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
        if (pedidoOpt.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado!");
        }

        Pedido pedido = pedidoOpt.get();
        pedido.setStatusPedido(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return new PedidoDTO(pedidoAtualizado);
    }

    public boolean deletarPedido(Long idPedido) {
        if (pedidoRepository.existsById(idPedido)) {
            pedidoRepository.deleteById(idPedido);
            return true;
        }
        return false;
    }
}
