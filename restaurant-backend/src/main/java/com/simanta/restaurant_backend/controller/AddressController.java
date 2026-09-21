package com.simanta.restaurant_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.simanta.restaurant_backend.dto.Address_Add_request_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_Add_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_Delete_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_Set_IsDefault_response_DTO;
import com.simanta.restaurant_backend.dto.Address_Update_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Address_View_response_USER_DTO;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/user/api/address")
public class AddressController {

    private final AddressService addressService;
    private final AuthRepository authRepository;

    public AddressController(AddressService addressService,AuthRepository authRepository) {
        this.addressService = addressService;
        this.authRepository = authRepository;
    }

    // Add Address
    @PostMapping("/add/{userid}")
    public ResponseEntity<Address_Add_response_USER_DTO> add_address(@PathVariable final Long userid,Authentication authentication,@RequestBody @Valid final Address_Add_request_USER_DTO address_Add_request_USER_DTO){

        final String email = authentication.getName(); 

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userid)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Address_Add_response_USER_DTO add_response = addressService.add_Address(userid, address_Add_request_USER_DTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(add_response);
    }


    // Update Address
    @PutMapping("/update/{userid}/{addressid}")
    public ResponseEntity<Address_Update_response_USER_DTO> update_address(@PathVariable final Long userid,
        Authentication authentication,
        @PathVariable final Long addressid,
        @RequestBody @Valid Address_Add_request_USER_DTO address_Add_request_USER_DTO){

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userid)){
            throw new ResponseStatusException(HttpStatus.CREATED,"Access Denied");
        }

        Address_Update_response_USER_DTO update_response = addressService.update_Address(userid, addressid,address_Add_request_USER_DTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(update_response);
    }


    // Set Address IsDefault
    @PutMapping("/setIsDefault/{userid}/{addressid}")
    public ResponseEntity<Address_Set_IsDefault_response_DTO> set_Address_IsDefault(@PathVariable final Long userid,@PathVariable final Long addressid,
        Authentication authentication){

            final String email = authentication.getName();

            final User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

            if(!loggedInUser.getId().equals(userid)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
            }

            Address_Set_IsDefault_response_DTO default_response = addressService.set_Address_IsDefault(userid, addressid);

        return ResponseEntity.status(HttpStatus.OK).body(default_response);
    }


    // Delete Address
    @DeleteMapping("/delete/{userid}/{addressid}")
    public ResponseEntity<Address_Delete_response_USER_DTO> delete_Address(@PathVariable final Long userid,Authentication authentication,@PathVariable final Long addressid){

            final String email = authentication.getName();

            final User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

            if(!loggedInUser.getId().equals(userid)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
            }

        Address_Delete_response_USER_DTO delete_response = addressService.delete_Address(userid, addressid);

        return ResponseEntity.status(HttpStatus.OK).body(delete_response);
    }


    // View Address
    @GetMapping("/view/{userid}")
    public ResponseEntity<List<Address_View_response_USER_DTO>> view_Address(@PathVariable final Long userid,Authentication authentication){

            final String email = authentication.getName();

            final User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

            if(!loggedInUser.getId().equals(userid)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
            }

        List<Address_View_response_USER_DTO> view_response = addressService.view_Address(userid);

        return ResponseEntity.status(HttpStatus.OK).body(view_response);
    }

}
