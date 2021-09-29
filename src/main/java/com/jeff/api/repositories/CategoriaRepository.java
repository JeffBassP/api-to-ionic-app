package com.jeff.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeff.api.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
