package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.ADMIN_Update_Profile_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Update_Profile_response_DTO;
import com.simanta.restaurant_backend.service.ADMIN_Update_Profile_Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/admin/AdminProfile")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ADMIN_Update_Profile_Controller {

    private final ADMIN_Update_Profile_Service admin_Update_Profile_Service;

    public ADMIN_Update_Profile_Controller(ADMIN_Update_Profile_Service admin_Update_Profile_Service){
        this.admin_Update_Profile_Service = admin_Update_Profile_Service;
    }

    @PutMapping("/updateAdminProfile/{userId}")
    public ResponseEntity<ADMIN_Update_Profile_response_DTO> updateAdminProfile(@PathVariable final Long userId,Authentication authentication,@RequestBody @Valid final ADMIN_Update_Profile_request_DTO admin_Update_Profile_request_DTO){

        final String loggedInEmail = authentication.getName();

        ADMIN_Update_Profile_response_DTO update_Profile_Response = admin_Update_Profile_Service.updateAdminProfile(userId, loggedInEmail, admin_Update_Profile_request_DTO);

        return ResponseEntity.status(HttpStatus.OK).body(update_Profile_Response);
    }

}
