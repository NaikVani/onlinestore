package com.online.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.store.model.AuthenticationToken;
import com.online.store.model.Customer;
import com.online.store.repository.TokenRepository;

@Service
public class AuthenticationService {

	@Autowired
	TokenRepository repository;

	public String saveConfirmationToken(AuthenticationToken authenticationToken) {
		AuthenticationToken token = repository.save(authenticationToken);
		return token.getToken();
	}
	
	public Customer getUser(AuthenticationToken token) {
		if (token.getCustomer() != null) {
			return token.getCustomer();
		}
		return null;
	}

	public Customer authenticate(String token) throws Exception {
		AuthenticationToken authenticationToken = checkIfTokenExists(token);
		if (authenticationToken == null) {
			throw new Exception("Authentication token not present");
		}
		Customer customer = getUser(authenticationToken);
		if (customer == null) {
			throw new Exception("Authentication Token Not valid");
		}
		return customer;
	}

	private AuthenticationToken checkIfTokenExists(String token) {
		return repository.findTokenByToken(token);
	}
}
