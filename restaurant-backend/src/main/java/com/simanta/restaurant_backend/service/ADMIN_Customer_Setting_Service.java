package com.simanta.restaurant_backend.service;
import org.springframework.stereotype.Service;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_response_DTO;
import com.simanta.restaurant_backend.model.ADMIN_Customer;
import com.simanta.restaurant_backend.repository.ADMIN_Customer_Setting_Repository;

import jakarta.transaction.Transactional;

@Service  
public class ADMIN_Customer_Setting_Service {

    private final ADMIN_Customer_Setting_Repository admin_Customer_Setting_Repository;

    public ADMIN_Customer_Setting_Service(ADMIN_Customer_Setting_Repository admin_Customer_Setting_Repository){
        this.admin_Customer_Setting_Repository = admin_Customer_Setting_Repository;
    }
 
    @Transactional
    public ADMIN_Customer_Setting_response_DTO Admin_setting(final ADMIN_Customer_Setting_request_DTO admin_Customer_Setting_request_DTO){

        ADMIN_Customer admin_Customer = new ADMIN_Customer();

        admin_Customer.setRestaurantname(admin_Customer_Setting_request_DTO.getRestaurantname());
        admin_Customer.setRestaurantphonenumber(admin_Customer_Setting_request_DTO.getRestaurantphonenumber());
        admin_Customer.setRestaurantaddress(admin_Customer_Setting_request_DTO.getRestaurantaddress());

        admin_Customer_Setting_Repository.save(admin_Customer);

        return new ADMIN_Customer_Setting_response_DTO("Settings updated");
    }

}
