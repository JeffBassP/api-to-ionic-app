package com.jeff.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Cliente;
import com.jeff.api.dto.ClienteDTO;
import com.jeff.api.repositories.ClienteRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Optional<Cliente> findOne(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		
		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo " + Cliente.class.getName())));
	}

	public Cliente update(Cliente obj) {
		Optional<Cliente> newObj = findOne(obj.getId());

		updateData(newObj, obj);
		return repository.save(newObj.get());
	}

	private void updateData(Optional<Cliente> newObj, Cliente obj) {
		newObj.get().setNome(obj.getNome());
		newObj.get().setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		findOne(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir um cliente pois há entidades relacionadas");

		}
	}

	public List<Cliente> findAll() {

		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
}
