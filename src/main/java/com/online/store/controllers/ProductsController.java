package com.online.store.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.online.store.model.Customer;
import com.online.store.model.Product;
import com.online.store.repository.ProductRepository;
import com.online.store.service.AuthenticationService;

@RestController
@RequestMapping("/products")
public class ProductsController {
	Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<String> findAllProducts(@RequestParam("token") String token) {
		
		Customer customer = null;
		try {
			customer = authenticationService.authenticate(token);
		} catch (Exception e) {
			logger.error("Error authenticating ");
		}
		if (customer == null) {
			return new ResponseEntity<String>("Invalid User", HttpStatus.UNAUTHORIZED);
		}
		
		List<Product> products = productRepository.findAll();
		return new ResponseEntity<String>(new Gson().toJson(products), HttpStatus.OK);
	}
}
