package com.simanta.restaurant_backend.service;

import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.ADMIN_Update_Profile_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Update_Profile_response_DTO;
import com.simanta.restaurant_backend.exception.ADMIN_Update_Profile_Service_Exception;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;

import jakarta.transaction.Transactional;

@Service
public class ADMIN_Update_Profile_Service {

    private final AuthRepository authRepository;

    public ADMIN_Update_Profile_Service(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Transactional
    public ADMIN_Update_Profile_response_DTO updateAdminProfile(final Long userId,final String loggedInEmail,final ADMIN_Update_Profile_request_DTO admin_Update_Profile_request_DTO){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new ADMIN_Update_Profile_Service_Exception("Admin not found"));

        if(!user.getEmail().equals(loggedInEmail)){
            throw new ADMIN_Update_Profile_Service_Exception("Access denied for this profile.");
        }

        user.setName(admin_Update_Profile_request_DTO.getName());
        user.setPhonenumber(admin_Update_Profile_request_DTO.getPhonenumber());
        user.setAddress(admin_Update_Profile_request_DTO.getAddress());

        authRepository.save(user);

        return new ADMIN_Update_Profile_response_DTO("Admin profile updated successfully.");
    }
}
    