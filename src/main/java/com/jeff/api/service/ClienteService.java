package com.jeff.api.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeff.api.domain.Cidade;
import com.jeff.api.domain.Cliente;
import com.jeff.api.domain.Endereco;
import com.jeff.api.domain.enums.Perfil;
import com.jeff.api.domain.enums.TipoCliente;
import com.jeff.api.dto.ClienteDTO;
import com.jeff.api.dto.ClienteNewDTO;
import com.jeff.api.repositories.CidadeRepository;
import com.jeff.api.repositories.ClienteRepository;
import com.jeff.api.repositories.EnderecoRepository;
import com.jeff.api.security.UserSS;
import com.jeff.api.service.exceptions.AuthorizationException;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3service;
	public Optional<Cliente> findOne(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasHole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Cliente> obj = repository.findById(id);

		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Cliente.class.getName())));
	}

	public Cliente update(Cliente obj) {
		Optional<Cliente> newObj = findOne(obj.getId());

		updateData(newObj, obj);
		return repository.save(newObj.get());
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityViolationException(
					"Não é possivel excluir um cliente pois há entidades relacionadas");

		}
	}

	public List<Cliente> findAll() {

		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());

		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = s3service.uploadFile(multipartFile);
		
		Cliente cli = repository.findById(user.getId()).get();
		cli.setImgUrl(uri.toString());
		
		repository.save(cli);
		
		return s3service.uploadFile(multipartFile);
	}
}
