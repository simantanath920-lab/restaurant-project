package com.simanta.restaurant_backend.dto;

public class Order_Status_Update_response_ADMIN_DTO {

    private String message;

    public Order_Status_Update_response_ADMIN_DTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
