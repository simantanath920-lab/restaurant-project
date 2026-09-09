package com.simanta.restaurant_backend.dto;

public class Update_Category_ADMIN_response_DTO {

    private Long id;
    private String name;
    private String message;
    private int status;

    public Update_Category_ADMIN_response_DTO(Long id,String name,String message,int status) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
