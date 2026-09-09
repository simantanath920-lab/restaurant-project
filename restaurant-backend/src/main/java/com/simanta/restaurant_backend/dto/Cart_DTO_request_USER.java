package com.simanta.restaurant_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Cart_DTO_request_USER {

    @NotNull(message = "Menu ID is required")
    private Long menuid;

    @NotNull(message = "Quantity is required")
    @Min(value = 1,message = "Quantity must be at least 1")
    private Integer quantity;


    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
