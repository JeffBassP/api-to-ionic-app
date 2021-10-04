package com.jeff.api.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.domain.Pedido;
import com.jeff.api.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	@Autowired
	private PedidoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Pedido>> find(@PathVariable Integer id){
		Optional<Pedido> obj = service.findOne(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
 }
