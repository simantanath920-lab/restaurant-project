package com.simanta.restaurant_backend.dto;

import java.util.List;

public class View_CART_response_DTO {

    private Long cartId;

    private List<View_CARTITEM_response_DTO> items;

    private Double totalPrice;


    public View_CART_response_DTO(Long cartId,List<View_CARTITEM_response_DTO> items,Double totalPrice) {
        this.cartId = cartId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<View_CARTITEM_response_DTO> getItems() {
        return items;
    }

    public void setItems(List<View_CARTITEM_response_DTO> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
