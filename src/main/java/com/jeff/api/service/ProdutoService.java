package com.jeff.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Categoria;
import com.jeff.api.domain.Produto;
import com.jeff.api.repositories.CategoriaRepository;
import com.jeff.api.repositories.ProdutoRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository repository;

	public Optional<Produto> findOne(Integer id) {
		Optional<Produto> obj = repository.findById(id);

		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Produto.class.getName())));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}
}
