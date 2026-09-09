package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.USER_Profile_MainPage_response_DTO;
import com.simanta.restaurant_backend.dto.USER_updateProfile_MainPage_request_DTO;
import com.simanta.restaurant_backend.dto.USER_updateProfile_MainPage_response_DTO;
import com.simanta.restaurant_backend.dto.USER_userChangePassword_MainPage_request_DTO;
import com.simanta.restaurant_backend.dto.USER_userChangePassword_MainPage_response_DTO;
import com.simanta.restaurant_backend.service.USER_Profile_MainPage_Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/user/homepage")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class USER_Profile_MainPage_Controller {

    private final USER_Profile_MainPage_Service mainPage_Service;
 
    public USER_Profile_MainPage_Controller(USER_Profile_MainPage_Service mainPage_Service){
        this.mainPage_Service = mainPage_Service;
    } 

    @GetMapping("/userProfile/{userId}")
    public ResponseEntity<USER_Profile_MainPage_response_DTO> userProfileDetails(@PathVariable final Long userId){

        USER_Profile_MainPage_response_DTO mainPage_response = mainPage_Service.userProfileDetails(userId);

        return ResponseEntity.status(HttpStatus.OK).body(mainPage_response);
    }
 

    @PutMapping("/updateUserProfile/{userId}")
    public ResponseEntity<USER_updateProfile_MainPage_response_DTO> updateUserProfileDetails(@PathVariable final Long userId,@RequestBody @Valid final USER_updateProfile_MainPage_request_DTO updateProfile_MainPage_request){

        USER_updateProfile_MainPage_response_DTO updateProfile_response = mainPage_Service.updateUserProfileDetails(userId, updateProfile_MainPage_request);
        
        return ResponseEntity.status(HttpStatus.OK).body(updateProfile_response);
    }


    @PutMapping("/userChangePassword/{userId}")
    public ResponseEntity<USER_userChangePassword_MainPage_response_DTO> changeUserPassword(@PathVariable final Long userId,@RequestBody @Valid USER_userChangePassword_MainPage_request_DTO changePassword_MainPage_request_DTO){

        USER_userChangePassword_MainPage_response_DTO changePassword_response = mainPage_Service.changeUserPassword(userId, changePassword_MainPage_request_DTO);

        return ResponseEntity.status(HttpStatus.OK).body(changePassword_response);
    }
} 
