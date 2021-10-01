package com.jeff.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeff.api.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
