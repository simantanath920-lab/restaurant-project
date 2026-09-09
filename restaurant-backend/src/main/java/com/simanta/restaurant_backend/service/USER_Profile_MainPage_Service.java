package com.simanta.restaurant_backend.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simanta.restaurant_backend.model.User; 
import com.simanta.restaurant_backend.dto.USER_Profile_MainPage_response_DTO;
import com.simanta.restaurant_backend.dto.USER_updateProfile_MainPage_request_DTO;
import com.simanta.restaurant_backend.dto.USER_updateProfile_MainPage_response_DTO;
import com.simanta.restaurant_backend.dto.USER_userChangePassword_MainPage_request_DTO;
import com.simanta.restaurant_backend.dto.USER_userChangePassword_MainPage_response_DTO;
import com.simanta.restaurant_backend.exception.USER_Profile_MainPage_Service_Exception;
import com.simanta.restaurant_backend.repository.AuthRepository;

@Service 
public class USER_Profile_MainPage_Service {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public USER_Profile_MainPage_Service(AuthRepository authRepository,PasswordEncoder passwordEncoder){
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    } 

    @Transactional(readOnly = true)
    public USER_Profile_MainPage_response_DTO userProfileDetails(final Long userId){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new USER_Profile_MainPage_Service_Exception("User not found"));

            USER_Profile_MainPage_response_DTO user_Profile = new USER_Profile_MainPage_response_DTO(user.getName(),
                         user.getEmail(), user.getPhonenumber(), user.getAddress());

        return user_Profile;
    }



    @Transactional
    public USER_updateProfile_MainPage_response_DTO updateUserProfileDetails(final Long userId,final USER_updateProfile_MainPage_request_DTO updateProfile_MainPage_request){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new USER_Profile_MainPage_Service_Exception("User not found"));

            user.setName(updateProfile_MainPage_request.getUpdatename());
            user.setPhonenumber(updateProfile_MainPage_request.getUpdatephonenumber());
            user.setAddress(updateProfile_MainPage_request.getUpdateaddress());
            user.setUpdatedAt(LocalDateTime.now());

        authRepository.save(user);

        return new USER_updateProfile_MainPage_response_DTO("User update successfully.");
    }



    @Transactional
    public USER_userChangePassword_MainPage_response_DTO changeUserPassword(final Long userId,final USER_userChangePassword_MainPage_request_DTO changePassword_MainPage_request_DTO){

        final User user = authRepository.findById(userId)
            .orElseThrow(()-> new USER_Profile_MainPage_Service_Exception("User not found"));

        if(!"USER".equals(user.getRole())){
            throw new USER_Profile_MainPage_Service_Exception("Access denied for this profile to change Password.");
        }

        if(!passwordEncoder.matches(changePassword_MainPage_request_DTO.getCurrentPassword(), user.getPassword())){
            throw new USER_Profile_MainPage_Service_Exception("Current password didn't match.");
        }

        final String encodePassword = passwordEncoder.encode(changePassword_MainPage_request_DTO.getNewPassword());

        user.setPassword(encodePassword);

        authRepository.save(user);

        return new USER_userChangePassword_MainPage_response_DTO("User password updated successfully.");
    }
 
}
