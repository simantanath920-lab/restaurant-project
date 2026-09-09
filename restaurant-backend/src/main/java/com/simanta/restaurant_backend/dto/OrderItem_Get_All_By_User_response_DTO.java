package com.simanta.restaurant_backend.dto;

public class OrderItem_Get_All_By_User_response_DTO {

    private Long order_id;

    private Long menu_id;

    private Integer quantity;

    private double price;

    public OrderItem_Get_All_By_User_response_DTO(Long order_id, Long menu_id, Integer quantity, double price) {
        this.order_id = order_id;
        this.menu_id = menu_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Long menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

}
