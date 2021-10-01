package com.jeff.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeff.api.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
