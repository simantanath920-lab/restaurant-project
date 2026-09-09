package com.simanta.restaurant_backend.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ADMIN_Update_Profile_request_DTO {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]{2,50}$",message = "Enter a valid name")
    private String name;

    @NotBlank(message = "Phone no is required")
    @Pattern(regexp = "^(\\+91|91)?[6-9][0-9]{9}$",message = "Enter a valid phone no")
    private String phonenumber;

    @NotBlank(message = "Address is required")
    @Pattern(regexp = "^[a-zA-Z0-9,./\\-\\s]+$",message = "Enter a valid address")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
