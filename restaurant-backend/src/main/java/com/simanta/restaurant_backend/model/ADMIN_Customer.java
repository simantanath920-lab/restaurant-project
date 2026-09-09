package com.simanta.restaurant_backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_customer") 
public class ADMIN_Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantname;

    private String restaurantphonenumber;

    private String restaurantaddress;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
