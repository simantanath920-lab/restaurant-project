package com.simanta.restaurant_backend.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_response_DTO;
import com.simanta.restaurant_backend.service.ADMIN_Customer_Setting_Service;
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/restaurant/admin/setting")
public class ADMIN_Customer_Setting_Controller {

    private final ADMIN_Customer_Setting_Service admin_Customer_Setting_Service;

    public ADMIN_Customer_Setting_Controller(ADMIN_Customer_Setting_Service admin_Customer_Setting_Service){
        this.admin_Customer_Setting_Service = admin_Customer_Setting_Service;
    }

    @PostMapping
    public ResponseEntity<ADMIN_Customer_Setting_response_DTO> admin_Setting(@RequestBody @Valid ADMIN_Customer_Setting_request_DTO ADMIN_Customer_Setting_request_DTO){

        ADMIN_Customer_Setting_response_DTO admin_customer_setting_response = admin_Customer_Setting_Service.Admin_setting(ADMIN_Customer_Setting_request_DTO);

        return ResponseEntity.status(HttpStatus.OK).body(admin_customer_setting_response);
    }

}
