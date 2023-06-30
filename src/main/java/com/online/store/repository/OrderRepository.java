package com.online.store.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.online.store.model.Customer;
import com.online.store.model.Order;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query("select o from Order o where o.customer=:customer")
	List<Order> findOrderbyCustomerId(Customer customer);
}
