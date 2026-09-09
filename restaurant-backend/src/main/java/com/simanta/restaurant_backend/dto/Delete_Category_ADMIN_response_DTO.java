package com.simanta.restaurant_backend.dto;

public class Delete_Category_ADMIN_response_DTO {

    private String name;

    private String message;

    public Delete_Category_ADMIN_response_DTO(String name,String message) {
        this.name = name;
        this.message = message;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
