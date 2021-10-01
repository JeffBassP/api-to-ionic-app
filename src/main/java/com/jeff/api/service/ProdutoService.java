package com.jeff.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Produto;
import com.jeff.api.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public Optional<Produto> findOne(Long id) {
		Optional<Produto> obj = repository.findById(id);
		return obj;
	}

}
