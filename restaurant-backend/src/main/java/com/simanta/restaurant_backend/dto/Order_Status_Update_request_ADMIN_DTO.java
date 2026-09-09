package com.simanta.restaurant_backend.dto;

import com.simanta.restaurant_backend.model.OrderStatus;

public class Order_Status_Update_request_ADMIN_DTO {

    private Long id;

    private OrderStatus orderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
