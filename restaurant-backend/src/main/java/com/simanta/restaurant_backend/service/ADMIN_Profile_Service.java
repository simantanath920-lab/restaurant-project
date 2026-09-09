package com.simanta.restaurant_backend.service;

import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.ADMIN_Profile_response_DTO;
import com.simanta.restaurant_backend.exception.ADMIN_Profile_Service_Exception;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;

@Service
public class ADMIN_Profile_Service {

    private final AuthRepository authRepository;

    public ADMIN_Profile_Service(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public ADMIN_Profile_response_DTO ViewAdminProfile(Long userId){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new ADMIN_Profile_Service_Exception("Admin not Found"));

            if("USER".equals(user.getRole())){
                throw new ADMIN_Profile_Service_Exception("Access denied for this Profile.");
            }
            
        return new ADMIN_Profile_response_DTO(user.getName(), user.getEmail(), user.getPhonenumber(), user.getAddress(), user.getRole());
    }
}
