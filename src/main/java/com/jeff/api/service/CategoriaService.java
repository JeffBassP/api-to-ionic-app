package com.jeff.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Categoria;
import com.jeff.api.repositories.CategoriaRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Optional<Categoria> findOne(Integer id) {
		Optional<Categoria> obj = repository.findById(id);

		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Categoria.class.getName())));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);

		return repository.save(obj);
	}

	public Categoria update(Categoria obj) {
		findOne(obj.getId());

		return repository.save(obj);
	}

	public void delete(Integer id) {
		findOne(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir uma categoria que possui produtos");

		}
	}

	public List<Categoria> findAll() {

		return repository.findAll();
	}

	
}
