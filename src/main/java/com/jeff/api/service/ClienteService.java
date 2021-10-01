package com.jeff.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Cliente;
import com.jeff.api.repositories.ClienteRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Optional<Cliente> findOne(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		
		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo " + Cliente.class.getName())));
	}

}
