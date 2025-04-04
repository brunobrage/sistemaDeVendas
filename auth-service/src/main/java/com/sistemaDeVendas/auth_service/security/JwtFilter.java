package com.sistemaDeVendas.auth_service.security;

import java.io.IOException;
import java.security.Key;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtFilter extends OncePerRequestFilter {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token não encontrado");
            return;
        }

        String token = header.substring(7);
        try {
            Jwts.parserBuilder()
                 .setSigningKey(getSignKey())
                 .build()
                 .parseClaimsJws(token);
        
            filterChain.doFilter(request, response);
        
            System.out.println("Chave no Auth: " + secretKey);
        } catch (ExpiredJwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token expirado");
        } catch (JwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token inválido");
        }
        
    }
}