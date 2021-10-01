package com.jeff.api.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.domain.Cliente;
import com.jeff.api.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/lista")
	public ResponseEntity<List<Cliente>> list() {
		
		List<Cliente> lista = service.findAll();

		return ResponseEntity.ok().body(lista);	
	}
	
	@GetMapping(value = "/lista/{id}")
	public ResponseEntity<Optional<Cliente>> find(@PathVariable Integer id){
		Optional<Cliente> obj = service.findOne(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
 }
