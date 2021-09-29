package com.jeff.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Categoria;
import com.jeff.api.repositories.CategoriaRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Optional<Categoria> findOne(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		
		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo " + Categoria.class.getName())));
	}

}
