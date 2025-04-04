package com.sistemaDeVendas.auth_service.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime; // Tempo de expiração em milissegundos

    // 🔹 Método para gerar o token JWT
    public String generateToken(String username) {
        byte[] secretBytes = Base64.getDecoder().decode(secretKey); // Decodifica a chave base64
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretBytes) // 🔥 Usa a chave decodificada
                .compact();
    }
}
