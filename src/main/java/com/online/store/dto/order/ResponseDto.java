package com.online.store.dto.order;

import java.util.List;

import com.online.store.model.Order;

public class ResponseDto {
    private Integer user_id;
    private List<Order> orderDetails;
    
    public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public List<Order> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<Order> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public ResponseDto() {
    }
	@Override
	public String toString() {
		return "ResponseDto [user_id=" + user_id + ", orderDetails=" + orderDetails + "]";
	}
}
