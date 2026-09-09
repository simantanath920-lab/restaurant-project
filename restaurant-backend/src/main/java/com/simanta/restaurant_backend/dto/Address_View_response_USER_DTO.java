package com.simanta.restaurant_backend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simanta.restaurant_backend.model.AddressType;

public class Address_View_response_USER_DTO {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String street;

    private String area;

    private String city;

    private String state;

    private String pincode;

    private String country;

    @JsonProperty("isDefault")
    private boolean isDefault;

    private AddressType addressType;

    public Address_View_response_USER_DTO(Long id,String fullName,String phoneNumber,String street,String area,String city,String state,String pincode,
        String country,boolean isDefault,AddressType addressType) {

            this.id = id;
            this.fullName = fullName;
            this.phoneNumber = phoneNumber;
            this.street = street;
            this.area = area;
            this.city = city;
            this.state = state;
            this.pincode = pincode;
            this.country = country;
            this.isDefault = isDefault;
            this.addressType = addressType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
}
