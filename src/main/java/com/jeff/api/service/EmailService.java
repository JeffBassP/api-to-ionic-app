package com.jeff.api.service;

import org.springframework.mail.SimpleMailMessage;

import com.jeff.api.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
