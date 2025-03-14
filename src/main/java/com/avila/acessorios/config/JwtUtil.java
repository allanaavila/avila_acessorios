package com.avila.acessorios.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {


    private static final String SECRET_KEY = "CHAVE_SUPER_SECRETA_COM_32BYTES_DE_SEGURANCA";

    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
