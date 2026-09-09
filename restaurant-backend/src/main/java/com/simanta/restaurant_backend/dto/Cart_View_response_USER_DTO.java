package com.simanta.restaurant_backend.dto;

public class Cart_View_response_USER_DTO {

    private Long menuId;

    private String menuName;

    private String imageUrl;

    private Double price;

    private Integer quantity;

    private Double subtotal;

    public Cart_View_response_USER_DTO(Long menuId, String menuName, String imageUrl ,Double price, Integer quantity, Double subtotal) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
