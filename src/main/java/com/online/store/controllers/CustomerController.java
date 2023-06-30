package com.online.store.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online.store.dto.customer.RequestDto;
import com.online.store.dto.customer.CreateDto;
import com.online.store.dto.customer.ResponseDto;
import com.online.store.dto.customer.UpdateDto;
import com.online.store.model.Customer;
import com.online.store.service.AuthenticationService;
import com.online.store.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
    @Autowired
    AuthenticationService authenticationService;
	
	@Autowired
	private CustomerService userService;

	@GetMapping
	public List<ResponseDto> findAllUser(String token) {
		return userService.findAll();
	}

	@PostMapping
	public String createUser(@RequestBody CreateDto user) {
		logger.info("Creating new User ");
		try {
			return userService.createUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "Error Creating user";
		}
	}

	@PutMapping
	public String updateUser(@RequestParam("token") String token, @RequestBody UpdateDto user) {
		logger.info("Updating the user : " + user);
		Customer customer = null;
		try {
			customer = authenticationService.authenticate(token);
		} catch (Exception e) {
			logger.error("Error authenticating ");
		}
		if (customer == null) {
			return "Invalid User";
		}
		try {
			return userService.updateCustomer(user, customer.getId());
		} catch (Exception e) {
			return "Error Updating user";
		}
	}

	@PutMapping("/changePassword")
	public String updatePassword(@RequestBody RequestDto dto, @RequestParam("token") String token) {
		logger.info("changing passowrd for the user " + dto.getEmail());
		Customer customer = null;
		try {
			customer = authenticationService.authenticate(token);
		} catch (Exception e) {
			logger.error("Error authenticating ");
		}
		if (customer == null || !customer.getEmail().equals(dto.getEmail())) {
			return "Invalid Token For the User";
		}
		try {
			return userService.updatePassword(dto);
		} catch (Exception e) {
			return "Error Updating Password";
		}
	}
	
	@GetMapping("/login")
	public String login(@RequestBody RequestDto requestDto) {
		logger.info("Generating logintoken for the user " + requestDto.getEmail());
		return userService.authenticateUser(requestDto);
	}
}
