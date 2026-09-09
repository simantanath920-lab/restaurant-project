package com.simanta.restaurant_backend.dto;

public class Cart_DTO_response_USER {

    private String message;

    public Cart_DTO_response_USER(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
