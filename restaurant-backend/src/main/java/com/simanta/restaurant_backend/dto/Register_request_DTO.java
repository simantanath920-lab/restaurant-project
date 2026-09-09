package com.simanta.restaurant_backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Register_request_DTO {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]{2,50}$",message = "Enter a valid name")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9]+@gmail\\.com$",message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Phone no is required")
    @Pattern(regexp = "^(\\+91|91)?[6-9][0-9]{9}$",message = "Enter a valid phone no")
    private String phonenumber;

    @NotBlank(message = "Address is required")
    @Pattern(regexp = "^[a-zA-Z0-9,./\\-\\s]+$",message = "Enter a valid address")
    private String address;
 
    @NotBlank(message = "Password is required")
    @Size(min = 6,message = "Password must be at least 6 characters") 
    private String password;

     
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
