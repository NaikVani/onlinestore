package com.online.store.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online.store.dto.order.CreateDto;
import com.online.store.dto.order.ResponseDto;
import com.online.store.model.Customer;
import com.online.store.service.AuthenticationService;
import com.online.store.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<String> placeOrder(@RequestBody CreateDto order, @RequestParam("token") String token) {
		logger.info("creating a new order " + order);
		Customer customer = null;
		try {
			customer = authenticationService.authenticate(token);
		} catch (Exception e) {
			logger.error("Error authenticating ");
		}
		if (customer == null) {
			return new ResponseEntity<String>("Invalid User", HttpStatus.UNAUTHORIZED);
		}

		String response = orderService.placeOrder(order, customer);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseDto findAllOrders(@RequestParam("token") String token) throws Exception {
		logger.info("getting all order information");
		Customer customer = null;
		try {
			customer = authenticationService.authenticate(token);
		} catch (Exception e) {
			logger.error("Error authenticating ");
			throw new Exception("Unauthorized");
		}
		if (customer == null) {
			logger.error("unauthorized");
		}
		return orderService.findAll(customer);
	}

	@DeleteMapping
	public ResponseEntity<String> cancelOrderById(@RequestParam("orderId") Integer orderId, @RequestParam("token") String token) {
		logger.info("Cancelling the order with id " + orderId);
		Customer customer = null;
		try {
			customer = authenticationService.authenticate(token);
		} catch (Exception e) {
			logger.error("Error authenticating ");
		}
		if (customer == null) {
			return new ResponseEntity<String>("Invalid User", HttpStatus.UNAUTHORIZED);
		}
		String response = orderService.deleteByOrderId(orderId, customer);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
