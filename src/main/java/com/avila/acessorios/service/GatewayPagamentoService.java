package com.avila.acessorios.service;

import com.avila.acessorios.config.GatewayPagamentoConfig;
import com.avila.acessorios.dto.GatewayResponseDTO;
import com.avila.acessorios.dto.PagamentoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GatewayPagamentoService {

    private final GatewayPagamentoConfig gatewayConfig;

    public GatewayPagamentoService(GatewayPagamentoConfig gatewayConfig) {
        this.gatewayConfig = gatewayConfig;
    }

    public GatewayResponseDTO processarPagamento(PagamentoDTO pagamentoDTO) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", pagamentoDTO.getValorTotal());
        requestBody.put("currency", "BRL");
        requestBody.put("payment_method", pagamentoDTO.getMetodoPagamento());
        requestBody.put("transaction_id", pagamentoDTO.getTransacaoID());

        try {
            System.out.println("Enviando requisição para o gateway: " + gatewayConfig.getUrl());
            ResponseEntity<String> response = restTemplate.postForEntity(gatewayConfig.getUrl(), requestBody, String.class);
            System.out.println("Resposta do gateway: " + response.getBody());
            return extrairGatewayResponse(response.getBody());
        } catch (Exception e) {
            System.err.println("Erro ao processar pagamento no gateway: " + e.getMessage());
            throw new RuntimeException("Erro ao processar pagamento no gateway.", e);
        }
    }

    private GatewayResponseDTO extrairGatewayResponse(String resposta) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resposta, GatewayResponseDTO.class);
        } catch (Exception e) {
            System.err.println("Erro ao extrair resposta do gateway: " + e.getMessage());
            throw new RuntimeException("Erro ao processar resposta do gateway.", e);
        }
    }

}
