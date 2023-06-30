package com.online.store.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.online.store.dto.customer.CreateDto;
import com.online.store.model.Customer;
import com.online.store.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Test
	public void createUserTest() throws Exception {
		CreateDto createDto = new CreateDto();
		createDto.setEmail("email");
		createDto.setFirstName("firstName");
		createDto.setLastName("lastName");
		createDto.setPassword("password");

		Customer customer = new Customer();
		customer.setId(1);

		when(customerRepository.findUserByEmail("email")).thenReturn(null);
		when(customerRepository.save(any())).thenReturn(customer);
		String str = customerService.createUser(createDto);
		Assertions.assertThat(str).isEqualTo(" User created with Id 1");
	}
}
