package com.simanta.restaurant_backend.dto;

public class Order_Cancel_response_USER_DTO {

    private String message;

    public Order_Cancel_response_USER_DTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
