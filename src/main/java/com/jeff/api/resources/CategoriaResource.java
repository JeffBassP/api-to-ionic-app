package com.jeff.api.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.domain.Categoria;
import com.jeff.api.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaService service;
	
	@GetMapping(value = "/lista")
	public ResponseEntity<List<Categoria>> list() {
		
		List<Categoria> lista = service.findAll();

		return ResponseEntity.ok().body(lista);	
	}
	
	@GetMapping(value = "/lista/{id}")
	public ResponseEntity<Optional<Categoria>> find(@PathVariable Integer id){
		Optional<Categoria> obj = service.findOne(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
 }
