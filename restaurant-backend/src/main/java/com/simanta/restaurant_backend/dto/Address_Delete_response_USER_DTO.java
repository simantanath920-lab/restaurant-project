package com.simanta.restaurant_backend.dto;

public class Address_Delete_response_USER_DTO {

    private String message;

    public Address_Delete_response_USER_DTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
