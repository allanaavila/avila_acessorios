package com.avila.acessorios.dto;


import com.avila.acessorios.model.Avaliacao;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AvaliacaoDTO {

    private Long idAvaliacao;
    private Long idUsuario;
    private Long idProduto;
    private int nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;


    public AvaliacaoDTO(Avaliacao avaliacao) {
        this.idAvaliacao = avaliacao.getIdAvaliacao();
        this.idUsuario = avaliacao.getUsuario().getIdUsuario();
        this.idProduto = avaliacao.getProduto().getIdProduto();
        this.nota = avaliacao.getNota();
        this.comentario = avaliacao.getComentario();
        this.dataAvaliacao = avaliacao.getDataAvaliacao();
    }
}
