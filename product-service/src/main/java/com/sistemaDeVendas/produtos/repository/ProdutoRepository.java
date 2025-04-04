package com.sistemaDeVendas.produtos.repository;

import com.sistemaDeVendas.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}