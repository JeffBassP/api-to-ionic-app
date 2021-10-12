package com.jeff.api.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.domain.Produto;
import com.jeff.api.dto.ProdutoDTO;
import com.jeff.api.resources.utils.URL;
import com.jeff.api.service.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	@Autowired
	private ProdutoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Produto>> find(@PathVariable Integer id){
		Optional<Produto> obj = service.findOne(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		String nomeDecoded = URL.decodeParam(nome);
		
		List<Integer> ids = URL.decodeIntList(categorias);
		
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDto);
		
	}
 }
 
