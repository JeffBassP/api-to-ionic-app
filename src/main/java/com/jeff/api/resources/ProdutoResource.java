package com.jeff.api.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.domain.Produto;
import com.jeff.api.service.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	@Autowired
	private ProdutoService service;
	
	@GetMapping(value = "/lista")
	public ResponseEntity<List<Produto>> list() {
		
		List<Produto> lista = service.findAll();

		return ResponseEntity.ok().body(lista);	
	}
	
	@GetMapping(value = "/lista/{id}")
	public ResponseEntity<Optional<Produto>> find(@PathVariable Long id){
		Optional<Produto> obj = service.findOne(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
 }
