package com.simanta.restaurant_backend.dto;

public class ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO {

    private String message;

    public ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
