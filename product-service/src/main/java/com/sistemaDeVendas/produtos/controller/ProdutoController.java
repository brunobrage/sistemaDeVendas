package com.sistemaDeVendas.produtos.controller;

import com.sistemaDeVendas.produtos.model.Produto;
import com.sistemaDeVendas.produtos.repository.ProdutoRepository;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/products/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

   @GetMapping
    public List<Produto>  getProdutos(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        return repository.findAll();
    }

    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        return repository.save(produto);
    }
}