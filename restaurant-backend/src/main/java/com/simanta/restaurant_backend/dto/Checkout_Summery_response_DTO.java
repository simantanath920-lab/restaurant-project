package com.simanta.restaurant_backend.dto;

import java.util.List;

public class Checkout_Summery_response_DTO {

    private List<Cart_View_response_USER_DTO> items;

    private Address_View_response_USER_DTO address;

    private double cartTotal;

    private double deliveryCharge;

    private double grandTotal;

    private boolean freeDelivery;


    public List<Cart_View_response_USER_DTO> getItems() {
        return items;
    }

    public void setItems(List<Cart_View_response_USER_DTO> items) {
        this.items = items;
    }

    public Address_View_response_USER_DTO getAddress() {
        return address;
    }

    public void setAddress(Address_View_response_USER_DTO address) {
        this.address = address;
    }

    public double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(double cartTotal) {
        this.cartTotal = cartTotal;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public boolean getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(boolean freeDelivery) {
        this.freeDelivery = freeDelivery;
    }
}
