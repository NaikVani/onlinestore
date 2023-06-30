package com.online.store.dto.order;

import java.util.List;

public class CreateDto {
	public List<Items> orderItems;

	public List<Items> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<Items> orderItems) {
		this.orderItems = orderItems;
	}
}
