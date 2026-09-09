package com.simanta.restaurant_backend.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_AdminAndUser_Management_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO;
import com.simanta.restaurant_backend.service.ADMIN_Customer_Setting_AdminAndUser_Management_Service;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/restaurant/admin/getDetailsOfsetting")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ADMIN_Customer_Setting_AdminAndUser_Management_Controller {

    private final ADMIN_Customer_Setting_AdminAndUser_Management_Service adminAndUser_Management_Service;

    public ADMIN_Customer_Setting_AdminAndUser_Management_Controller(ADMIN_Customer_Setting_AdminAndUser_Management_Service adminAndUser_Management_Service){
        this.adminAndUser_Management_Service = adminAndUser_Management_Service;
    }

    @PutMapping("/AdminAndUser/management")
    public ResponseEntity<ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO> adminAnduserManage(@RequestBody @Valid ADMIN_Customer_Setting_AdminAndUser_Management_request_DTO adminAndUser_Management_request_DTO,Authentication authentication){

        final String loggedInEmail = authentication.getName();

        ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO andUser_Management_response = adminAndUser_Management_Service.adminAnduserManage(adminAndUser_Management_request_DTO,loggedInEmail);

        return ResponseEntity.status(HttpStatus.OK).body(andUser_Management_response);
    }
}
