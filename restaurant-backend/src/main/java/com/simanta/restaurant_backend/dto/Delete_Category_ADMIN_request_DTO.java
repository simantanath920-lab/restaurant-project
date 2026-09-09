package com.simanta.restaurant_backend.dto;
import jakarta.validation.constraints.NotNull;

public class Delete_Category_ADMIN_request_DTO {

    @NotNull(message = "Available / Non-Available option is required")
    private Boolean isAvailable;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
