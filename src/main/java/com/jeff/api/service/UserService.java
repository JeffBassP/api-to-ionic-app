package com.jeff.api.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jeff.api.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try{
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
}
