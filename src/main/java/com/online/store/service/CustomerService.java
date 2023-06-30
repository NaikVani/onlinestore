package com.online.store.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.store.dto.customer.CreateDto;
import com.online.store.dto.customer.RequestDto;
import com.online.store.dto.customer.ResponseDto;
import com.online.store.dto.customer.UpdateDto;
import com.online.store.model.AuthenticationToken;
import com.online.store.model.Customer;
import com.online.store.repository.CustomerRepository;

@Service
public class CustomerService {
	Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AuthenticationService authenticationService;

	public String createUser(CreateDto customeCreateDto) throws Exception {

		String encryptedPassword = customeCreateDto.getPassword();
		try {
			encryptedPassword = hashPassword(customeCreateDto.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error("hashing password failed {}", e.getMessage());
		}
		if (customerRepository.findUserByEmail(customeCreateDto.getEmail()) != null) {
			return "There is an existing user with this email";
		}
		Customer user = new Customer(customeCreateDto.getFirstName(), customeCreateDto.getLastName(),
				customeCreateDto.getEmail(), encryptedPassword);
		try {
			Customer createdUser = customerRepository.save(user);
			return " User created with Id " + String.valueOf(createdUser.getId());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash;
	}

	public String updateCustomer(UpdateDto customer, Integer id) {
		customerRepository.updateUserInfoById(customer.getFirstName(), customer.getLastName(), id);
		return "User details updated successfully for Id " + id;
	}

	public String updatePassword(RequestDto dto) {
		try {
			customerRepository.changePassword(dto.getEmail(), hashPassword(dto.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}

		return "Password changed successfully for email : " + dto.getEmail();
	}

	public List<ResponseDto> findAll() {
		List<Customer> customers = customerRepository.findAll();
		List<ResponseDto> customersList = new ArrayList<>();
		for (Customer customer : customers) {
			ResponseDto response = new ResponseDto();
			response.setEmail(customer.getEmail());
			response.setFirstName(customer.getFirstName());
			response.setLastName(customer.getLastName());
			response.setId(customer.getId());
			customersList.add(response);
		}
		return customersList;
	}

	public String authenticateUser(RequestDto requestDto) {
		Customer customer = customerRepository.findUserByEmail(requestDto.getEmail());
		if (customer == null) {
			return "User does not exist";
		}

		String encryptedPassword = requestDto.getPassword();
		try {
			encryptedPassword = hashPassword(encryptedPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error("hashing password failed {}", e.getMessage());
		}

		if (!customer.getPassword().equals(encryptedPassword)) {
			return "Password does not match for the email " + requestDto.getEmail();
		}

		final AuthenticationToken authenticationToken = new AuthenticationToken(customer);

		String token = authenticationService.saveConfirmationToken(authenticationToken);

		return "Use this token for all API request : " + token;
	}
}
