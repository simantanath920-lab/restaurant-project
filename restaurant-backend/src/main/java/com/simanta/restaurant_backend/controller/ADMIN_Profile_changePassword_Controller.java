package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.ADMIN_Profile_changePassword_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Profile_changePassword_response_DTO;
import com.simanta.restaurant_backend.service.ADMIN_Profile_changePassword_Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/admin/AdminProfile")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ADMIN_Profile_changePassword_Controller {

    private final ADMIN_Profile_changePassword_Service admin_Profile_changePassword_Service;

    public ADMIN_Profile_changePassword_Controller(ADMIN_Profile_changePassword_Service admin_Profile_changePassword_Service){
        this.admin_Profile_changePassword_Service = admin_Profile_changePassword_Service;
    }

    @PutMapping("/ChangeAdminPassword/{userId}")
    public ResponseEntity<ADMIN_Profile_changePassword_response_DTO> changePassword(@PathVariable final Long userId,@RequestBody @Valid final ADMIN_Profile_changePassword_request_DTO admin_Profile_changePassword_request_DTO){

        ADMIN_Profile_changePassword_response_DTO changePassword_response_DTO = admin_Profile_changePassword_Service.changePassword(userId, admin_Profile_changePassword_request_DTO);

        return ResponseEntity.status(HttpStatus.OK).body(changePassword_response_DTO);
    }
}
