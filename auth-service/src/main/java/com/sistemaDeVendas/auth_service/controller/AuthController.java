package com.sistemaDeVendas.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaDeVendas.auth_service.model.UsuarioDTO;
import com.sistemaDeVendas.auth_service.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuario) {
        System.out.println("➡ Tentando login com: " + usuario.getUsername() + " e senha: " + usuario.getPassword());

        if ("admin".equals(usuario.getUsername()) && "1234".equals(usuario.getPassword())) {
            System.out.println("✅ Login bem-sucedido! Gerando token...");
            String token = jwtUtil.generateToken(usuario.getUsername()); // CORRIGIDO
            return ResponseEntity.ok(token);
        }

        System.out.println("❌ Login falhou! Credenciais incorretas.");
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
