package com.online.store.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.online.store.dto.order.CreateDto;
import com.online.store.dto.order.Items;
import com.online.store.model.Customer;
import com.online.store.model.Order;
import com.online.store.model.OrderItem;
import com.online.store.model.Product;
import com.online.store.repository.CustomerRepository;
import com.online.store.repository.OrderItemsRepository;
import com.online.store.repository.OrderRepository;
import com.online.store.repository.ProductRepository;

@SpringBootTest
public class OrderServiceTest {
	
	@InjectMocks
	private OrderService orderService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private OrderItemsRepository orderItemsRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Test
	public void placeOrderTest() {
		Items items1 = new Items();
		items1.setProductId(1);
		items1.setQuantity(2);
		List<Items> list = new ArrayList<>();
		list.add(items1);
		CreateDto createDto = new CreateDto();
		Optional<Customer> customer = Optional.of(new Customer());
		customer.get().setId(1);
		when(customerRepository.findById(1)).thenReturn(customer);
		createDto.setOrderItems(list);
		Order order = new Order();
		when(orderRepository.save(any())).thenReturn(order);
		Optional<Product> product = Optional.of(new Product());
		product.get().setPrice(300);
		when(productRepository.findById(1)).thenReturn(product);
		OrderItem itemsDto = new OrderItem();
		when(orderItemsRepository.save(any())).thenReturn(itemsDto);
		Customer user = new Customer(); user.setId(1);
		String str = orderService.placeOrder(createDto, user);
		Assertions.assertThat(str).contains("Order Placed Successfully with Id");

	}
	
}
