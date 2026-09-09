package com.simanta.restaurant_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.ADMIN_Customer_Panel_DTO_response;
import com.simanta.restaurant_backend.service.ADMIN_Panel_CustomerService;

@RestController
@RequestMapping("/restaurant/admin/customer")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ADMIN_Panel_CustomerController {

    private final ADMIN_Panel_CustomerService admin_Panel_CustomerService;

    public ADMIN_Panel_CustomerController(ADMIN_Panel_CustomerService admin_Panel_CustomerService){
        this.admin_Panel_CustomerService = admin_Panel_CustomerService;
    }

    @GetMapping
    public ResponseEntity<List<ADMIN_Customer_Panel_DTO_response>> getAllUserDetails(){

        List<ADMIN_Customer_Panel_DTO_response> getAllUserResponse = admin_Panel_CustomerService.getAllUser();

        return ResponseEntity.status(HttpStatus.OK).body(getAllUserResponse);
    } 

}
