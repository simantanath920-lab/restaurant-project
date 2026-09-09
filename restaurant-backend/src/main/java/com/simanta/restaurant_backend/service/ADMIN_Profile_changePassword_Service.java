package com.simanta.restaurant_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.ADMIN_Profile_changePassword_request_DTO;
import com.simanta.restaurant_backend.dto.ADMIN_Profile_changePassword_response_DTO;
import com.simanta.restaurant_backend.exception.DMIN_Profile_changePassword_Exception;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;

import jakarta.transaction.Transactional;

@Service
public class ADMIN_Profile_changePassword_Service {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public ADMIN_Profile_changePassword_Service(AuthRepository authRepository,PasswordEncoder passwordEncoder){
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public ADMIN_Profile_changePassword_response_DTO changePassword(final Long userId,final ADMIN_Profile_changePassword_request_DTO admin_Profile_changePassword_request_DTO){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new DMIN_Profile_changePassword_Exception("Admin not found"));

        if(!"ADMIN".equals(user.getRole())){
            throw new DMIN_Profile_changePassword_Exception("Access denied for this profile.");
        }

        if(!passwordEncoder.matches(admin_Profile_changePassword_request_DTO.getCurrentPassword(), user.getPassword())){
            throw new DMIN_Profile_changePassword_Exception("Current password is wrong.");
        }

        final String encodeNewPassword = passwordEncoder.encode(admin_Profile_changePassword_request_DTO.getNewPassword());

        user.setPassword(encodeNewPassword);

        authRepository.save(user);

        return new ADMIN_Profile_changePassword_response_DTO("Admin password updated successfully");
    }
}
