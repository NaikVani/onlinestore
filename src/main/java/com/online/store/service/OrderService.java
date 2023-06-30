package com.online.store.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.store.dto.order.CreateDto;
import com.online.store.dto.order.Items;
import com.online.store.dto.order.ResponseDto;
import com.online.store.model.Customer;
import com.online.store.model.Order;
import com.online.store.model.OrderItem;
import com.online.store.model.Product;
import com.online.store.repository.OrderItemsRepository;
import com.online.store.repository.OrderRepository;
import com.online.store.repository.ProductRepository;

@Service
public class OrderService {
    Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	public String placeOrder(CreateDto order, Customer customer) {
		Double totalPrice = 0.0;
		Order saveOrder = new Order();
		java.util.Date currentDate = new java.util.Date();
		saveOrder.setCreatedDate(new Date(currentDate.getTime()));
		saveOrder.setCustomer(customer);
		orderRepository.save(saveOrder);
		List<OrderItem> listItems = new ArrayList<>();
		for (Items item : order.orderItems ) {
			Optional<Product> product = productRepository.findById(item.getProductId());
			if (product.get().equals(null)) {
				logger.error(item.getProductId() + " product id does not exist");
			} else {
				OrderItem itemsDto = new OrderItem();
				itemsDto.setCreatedDate(currentDate);
				itemsDto.setOrder(saveOrder);
				double price = (item.getQuantity())*(product.get().getPrice());
				totalPrice = totalPrice + price;				
				itemsDto.setPrice(price);
				itemsDto.setProduct(product.get());
				itemsDto.setQuantity(item.getQuantity());
				orderItemsRepository.save(itemsDto);
				listItems.add(itemsDto);
			}
		}
		
		saveOrder.setOrderItems(listItems);
		saveOrder.setTotalPrice(totalPrice);
		orderRepository.save(saveOrder);
		return "Order Placed Successfully with Id " + saveOrder.getId() ;
	}

	public ResponseDto findAll(Customer customer) {
			List<Order> allOrders = orderRepository.findOrderbyCustomerId(customer);
			ResponseDto response = new ResponseDto();
			response.setUser_id(customer.getId());
			response.setOrderDetails(allOrders);
			return response;
	}

	public String deleteByOrderId(Integer order_id, Customer customer ) {
		Optional<Order> order = orderRepository.findById(order_id);
		if (order.get().equals(null)) {
			return "Order does not exist with ID :"+ order_id;
		}
		if (order.get().getCustomer().equals(customer)) {
			for (OrderItem item: order.get().getOrderItems()) {
				orderItemsRepository.delete(item);
			}
			orderRepository.deleteById(order_id);
		} else {
			return "There is no order for this user";
		}
		return " Order Cancelled Successfully with ID " + order_id;
	}

}
