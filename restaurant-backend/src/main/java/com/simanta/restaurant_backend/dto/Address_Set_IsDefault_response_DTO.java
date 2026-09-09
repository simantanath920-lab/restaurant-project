package com.simanta.restaurant_backend.dto;

public class Address_Set_IsDefault_response_DTO {

    private String message;

    public Address_Set_IsDefault_response_DTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
