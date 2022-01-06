package com.jeff.api.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeff.api.dto.EmailDTO;
import com.jeff.api.security.JWTUtil;
import com.jeff.api.security.UserSS;
import com.jeff.api.service.AuthService;
import com.jeff.api.service.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService service;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);

		return ResponseEntity.noContent().build();

	}

	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		service.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();

	}

}
