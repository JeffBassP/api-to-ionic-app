package com.jeff.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeff.api.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
