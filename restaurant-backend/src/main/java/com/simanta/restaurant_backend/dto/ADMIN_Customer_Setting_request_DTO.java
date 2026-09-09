package com.simanta.restaurant_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ADMIN_Customer_Setting_request_DTO {
 
    @NotBlank(message = "Enter your Restaurant Name")
    private String restaurantname;

    @NotBlank(message = "Enter your Restaurant Phone No")
    @Pattern(regexp = "^(\\+91|91)?[6-9][0-9]{9}$",message = "Enter a valid Restaurant phone no")
    private String restaurantphonenumber; 

    @NotBlank(message = "Enter your Restaurant Address")
    @Pattern(regexp = "^[a-zA-Z0-9,./\\-\\s]+$",message = "Enter a valid Restaurant address")
    private String restaurantaddress;


    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public String getRestaurantphonenumber() {
        return restaurantphonenumber;
    }

    public void setRestaurantphonenumber(String restaurantphonenumber) {
        this.restaurantphonenumber = restaurantphonenumber;
    }

    public String getRestaurantaddress() {
        return restaurantaddress;
    }

    public void setRestaurantaddress(String restaurantaddress) {
        this.restaurantaddress = restaurantaddress;
    }
}
