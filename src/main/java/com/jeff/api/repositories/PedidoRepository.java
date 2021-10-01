package com.jeff.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeff.api.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
