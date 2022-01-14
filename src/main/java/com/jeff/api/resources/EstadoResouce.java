package com.jeff.api.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.domain.Cidade;
import com.jeff.api.domain.Estado;
import com.jeff.api.dto.CidadeDTO;
import com.jeff.api.dto.EstadoDTO;
import com.jeff.api.service.CidadeService;
import com.jeff.api.service.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResouce {
	@Autowired
	private EstadoService service;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findEstados() {
		List<Estado> list = service.findByName();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value = "/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		List<Cidade> cidades = cidadeService.find(estadoId);
		List<CidadeDTO> cidadesDto = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(cidadesDto);
		

	}
}
