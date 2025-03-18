package com.avila.acessorios.service;

import com.avila.acessorios.dto.PedidoDTO;
import com.avila.acessorios.dto.PedidoDetalhadoDTO;
import com.avila.acessorios.model.*;
import com.avila.acessorios.model.Pagamentos.StatusPagamento;
import com.avila.acessorios.repository.*;
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

    @Autowired
    private ItemPedidoRepository pedidoItemRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PedidoDTO criarPedido(Long idUsuario, Long idEnderecoEntrega) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        Optional<Endereco> endereco = enderecoRepository.findByIdWithUsuario(idEnderecoEntrega);

        if (usuario.isEmpty() || endereco.isEmpty()) {
            throw new RuntimeException("Usuário ou endereço não encontrado!");
        }

        if (!endereco.get().getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new RuntimeException("O endereço informado não pertence ao usuário!");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario.get());
        pedido.setEnderecoEntrega(endereco.get());
        pedido.setTotalPedido(BigDecimal.ZERO);
        pedido.setStatusPedido(StatusPedido.PENDENTE);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new PedidoDTO(pedidoSalvo);
    }



    public List<PedidoDTO> listarPedidosPorUsuario(Long idUsuario, int page, int size) {
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

        if (novoStatus.name().equalsIgnoreCase("PAGO") || novoStatus.name().equalsIgnoreCase("APROVADO")) {
            throw new RuntimeException("O status do pedido não pode ser alterado para PAGO/APROVADO manualmente!");
        }

        Pedido pedido = pedidoOpt.get();

        if (novoStatus == StatusPedido.ENTREGUE) {
            Optional<Pagamento> pagamentoOpt = pagamentoRepository.findByPedidoIdPedido(idPedido);
            if (pagamentoOpt.isEmpty() || pagamentoOpt.get().getStatusPagamento() != StatusPagamento.APROVADO) {
                throw new RuntimeException("Não é possível marcar como ENTREGUE antes do pagamento ser aprovado!");
            }
        }

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


    public PedidoDetalhadoDTO buscarDetalhesPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

        Optional<Pagamento> pagamento = pagamentoRepository.findByPedidoIdPedido(idPedido);

        List<String> produtos = pedidoItemRepository.findByPedidoIdPedido(idPedido).stream()
                .map(item -> item.getProduto().getNome())
                .collect(Collectors.toList());

        PedidoDetalhadoDTO dto = new PedidoDetalhadoDTO();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setNomeUsuario(pedido.getUsuario().getNome());
        dto.setCpfUsuario(pedido.getUsuario().getCpf());
        dto.setProdutos(produtos);
        dto.setTotalPedido(pedido.getTotalPedido());
        dto.setStatusPedido(pedido.getStatusPedido());
        dto.setStatusPagamento(pagamento.isPresent() ? pagamento.get().getStatusPagamento() : null);

        return dto;
    }

}
