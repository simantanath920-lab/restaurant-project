package com.simanta.restaurant_backend.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_AdminAndUser_Management_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO;
import com.simanta.restaurant_backend.exception.ADMIN_Customer_Setting_AdminAndUser_Management;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;

import jakarta.transaction.Transactional;
 
@Service
public class ADMIN_Customer_Setting_AdminAndUser_Management_Service {

    private final AuthRepository authRepository;

    public ADMIN_Customer_Setting_AdminAndUser_Management_Service(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Transactional
    public ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO adminAnduserManage(final ADMIN_Customer_Setting_AdminAndUser_Management_request_DTO adminAndUser_Management_request_DTO,String loggedInEmail){

        final User user = authRepository.findByEmail(adminAndUser_Management_request_DTO.getEmail())
            .orElseThrow(()-> new ADMIN_Customer_Setting_AdminAndUser_Management("Profile not found"));

            if(user.getEmail().equals(loggedInEmail) && "USER".equals(adminAndUser_Management_request_DTO.getRole())){
                throw new ADMIN_Customer_Setting_AdminAndUser_Management("You cannot change your own role from ADMIN to USER");
            }

            user.setRole(adminAndUser_Management_request_DTO.getRole());
            user.setUpdatedAt(LocalDateTime.now());

            authRepository.save(user);

            if(adminAndUser_Management_request_DTO.getRole().equals("ADMIN")){
                return new ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO(user.getName() + "'s Profile has been promoted to ADMIN");
            }
            else{
                return new ADMIN_Customer_Setting_AdminAndUser_Management_response_DTO(user.getName() + "'s Profile has been admited to USER");
            }
    }  
}
