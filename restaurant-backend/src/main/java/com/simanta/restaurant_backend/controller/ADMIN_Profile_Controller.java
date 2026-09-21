package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.ADMIN_Profile_response_DTO;
import com.simanta.restaurant_backend.service.ADMIN_Profile_Service;

@RestController
@RequestMapping("/restaurant/admin/AdminProfile")
public class ADMIN_Profile_Controller {

    private final ADMIN_Profile_Service admin_Profile_Service;

    public ADMIN_Profile_Controller(ADMIN_Profile_Service admin_Profile_Service){
        this.admin_Profile_Service = admin_Profile_Service;
    }

    @GetMapping("/viewAdminDetails/{userId}")
    public ResponseEntity<ADMIN_Profile_response_DTO> ViewAdminProfile(@PathVariable final Long userId){

        ADMIN_Profile_response_DTO profile_response = admin_Profile_Service.ViewAdminProfile(userId);

        return ResponseEntity.status(HttpStatus.OK).body(profile_response);
    }
}
