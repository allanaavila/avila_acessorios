package com.avila.acessorios.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secretKey;

    public String gerarToken(String email, String tipoUsuario) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .claim("role", tipoUsuario)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token: " + e.getMessage(), e);
        }
    }


    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
