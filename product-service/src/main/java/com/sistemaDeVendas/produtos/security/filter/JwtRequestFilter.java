package com.sistemaDeVendas.produtos.security.filter;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    public JwtRequestFilter(SecretKey jwtSecretKey) {
        this.secretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            System.out.println("📦 Header Authorization: " + request.getHeader("Authorization"));
            System.out.println("🔑 SecretKey (alg): " + secretKey.getAlgorithm());
            System.out.println("🔑 SecretKey (format): " + secretKey.getFormat());
            System.out.println("🔑 SecretKey (length): " + secretKey.getEncoded().length + " bytes");
            System.out.println("🔑 [Product] Chave usada: " + 
                Base64.getEncoder().encodeToString(secretKey.getEncoded()));

            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

            chain.doFilter(request, response); 

        } catch (SignatureException e) {
            System.out.println("🔍 Token recebido: " + token);
            System.err.println("❌ Assinatura inválida. Verifique:");
            System.err.println("1. Chave igual entre auth e product");
            System.err.println("2. Token gerado com a mesma chave");
            System.err.println("3. Chave com pelo menos 256 bits (32 bytes)");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        }
    }
}
