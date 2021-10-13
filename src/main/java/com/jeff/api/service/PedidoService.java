package com.jeff.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.ItemPedido;
import com.jeff.api.domain.PagamentoComBoleto;
import com.jeff.api.domain.Pedido;
import com.jeff.api.domain.enums.EstadoPagamento;
import com.jeff.api.repositories.ItemPedidoRepository;
import com.jeff.api.repositories.PagamentoRepository;
import com.jeff.api.repositories.PedidoRepository;
import com.jeff.api.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private BoletoService boletoService;

	public Optional<Pedido> findOne(Integer id) {
		Optional<Pedido> obj = repository.findById(id);

		return Optional.of(obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Pedido.class.getName())));
	}

	
	public Pedido insert(Pedido obj) {

		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.getProduto();
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.findOne(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());

		return obj;
	}

}
