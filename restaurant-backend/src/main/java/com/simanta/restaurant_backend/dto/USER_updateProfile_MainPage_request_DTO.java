package com.simanta.restaurant_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class USER_updateProfile_MainPage_request_DTO {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]{2,50}$",message = "Enter a valid name")
    private String updatename; 

    @NotBlank(message = "Phone no is required")
    @Pattern(regexp = "^(\\+91|91)?[6-9][0-9]{9}$",message = "Enter a valid phone no")
    private String updatephonenumber;

    @NotBlank(message = "Address is required")
    @Pattern(regexp = "^[a-zA-Z0-9,./\\-\\s]+$",message = "Enter a valid address")
    private String updateaddress;

    public USER_updateProfile_MainPage_request_DTO(String updatename,String updatephonenumber,String updateaddress){
        this.updatename = updatename;
        this.updatephonenumber = updatephonenumber;
        this.updateaddress = updateaddress;
    }

    public String getUpdatename() {
        return updatename;
    }

    public void setUpdatename(String updatename) {
        this.updatename = updatename;
    }

    public String getUpdatephonenumber() {
        return updatephonenumber;
    }

    public void setUpdatephonenumber(String updatephonenumber) {
        this.updatephonenumber = updatephonenumber;
    }

    public String getUpdateaddress() {
        return updateaddress;
    }

    public void setUpdateaddress(String updateaddress) {
        this.updateaddress = updateaddress;
    }

    
}
