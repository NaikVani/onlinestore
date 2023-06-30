package com.online.store.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.store.model.OrderItem;

@Repository
@Transactional
public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {
	
}
