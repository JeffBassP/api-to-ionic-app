package com.jeff.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando servico de email");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
		
	}

}
