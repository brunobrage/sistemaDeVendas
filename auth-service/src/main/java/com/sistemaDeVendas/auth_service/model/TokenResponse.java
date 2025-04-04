package com.sistemaDeVendas.auth_service.model;

public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
}