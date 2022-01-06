package com.jeff.api.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.jeff.api.domain.Cliente;
import com.jeff.api.domain.Pedido;

@Service
public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
