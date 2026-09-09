package com.simanta.restaurant_backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Update_Menu_ADMIN_request_DTO { 

    @NotBlank(message = "Menu name is required")
    @Pattern(regexp = "^[A-Za-z ]{2,50}$",message = "Enter a valid menu name")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 300, message = "Description must be between 5 and 300 characters")
    private String description;
 
    @NotNull(message = "Price is required")
    @DecimalMin(value = "1.0", message = "Price must be greater than 0")
    private Double price; 

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Veg/Non-Veg status is required")
    private Boolean isVeg;

    @NotNull(message = "Preparation time is required")
    @Min(value = 1, message = "Preparation time must be at least 1 minute")
    @Max(value = 300, message = "Preparation time cannot exceed 300 minutes")
    private Integer preprationtime;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotNull(message = "Category id is required")
    @Positive(message = "Category ID must be greater than 0")
    private Long category_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public Integer getPreprationtime() {
        return preprationtime;
    }

    public void setPreprationtime(Integer preprationtime) {
        this.preprationtime = preprationtime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
} 
