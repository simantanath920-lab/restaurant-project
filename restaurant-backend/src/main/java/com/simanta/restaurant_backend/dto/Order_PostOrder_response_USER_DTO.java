package com.simanta.restaurant_backend.dto;

public class Order_PostOrder_response_USER_DTO {

    private String message;

    private Long orderid;

    public Order_PostOrder_response_USER_DTO(String message,Long orderid) {
        this.message = message;
        this.orderid = orderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Long getOrderid() {
        return orderid;
    }


    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
  
}
