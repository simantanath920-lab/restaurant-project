package com.simanta.restaurant_backend.dto;

public class Cart_DELETE_response_DTO {

    private String message;

    public Cart_DELETE_response_DTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
