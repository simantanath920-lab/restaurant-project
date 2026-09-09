package com.simanta.restaurant_backend.dto;

import java.time.LocalDateTime;

public class ADMIN_Customer_Panel_DTO_response {

    private String name;

    private String email;

    private String phonenumber;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private Long totalOrders;

    public ADMIN_Customer_Panel_DTO_response(String name,String email,String phonenumber,Boolean isActive,LocalDateTime createdAt,Long totalOrders){
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.totalOrders = totalOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }
}
