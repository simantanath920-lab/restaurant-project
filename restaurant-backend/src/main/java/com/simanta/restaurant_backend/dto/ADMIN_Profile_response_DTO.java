package com.simanta.restaurant_backend.dto;

public class ADMIN_Profile_response_DTO {

    private String name;

    private String email;

    private String phonenumber;

    private String address;

    private String role;

    public ADMIN_Profile_response_DTO(String name,String email,String phonenumber,String address,String role){
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.role = role;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
