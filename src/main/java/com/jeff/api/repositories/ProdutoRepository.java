package com.jeff.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeff.api.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
