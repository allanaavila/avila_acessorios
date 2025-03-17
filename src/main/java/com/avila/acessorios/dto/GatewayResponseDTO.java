package com.avila.acessorios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import lombok.Setter;



@Getter
@Setter
public class GatewayResponseDTO {
    @JsonProperty("transacao_id")
    private String transacaoID;

    private String status;
    private String mensagem;
}
