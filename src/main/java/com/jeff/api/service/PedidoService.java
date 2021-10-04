package com.jeff.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Pedido;
import com.jeff.api.repositories.PedidoRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public Optional<Pedido> findOne(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		
		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo " + Pedido.class.getName())));
	}

}
