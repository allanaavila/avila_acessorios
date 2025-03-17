package com.avila.acessorios.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/fake-gateway")
public class FakeGatewayController {

    @PostMapping("/processar")
    public ResponseEntity<Map<String, String>> processarPagamento(@RequestBody Map<String, Object> payload) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("transacao_id", "fake-transacao-" + UUID.randomUUID());
        resposta.put("status", "CONFIRMADO");
        return ResponseEntity.ok(resposta);
    }
}
