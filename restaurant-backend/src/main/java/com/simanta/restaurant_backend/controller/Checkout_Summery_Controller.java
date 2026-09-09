package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.simanta.restaurant_backend.dto.Checkout_Summery_response_DTO;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.service.Checkout_Summery_Service;

@RestController
@RequestMapping("/restaurant/user/api/checkout-summary")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class Checkout_Summery_Controller {

    private final AuthRepository authRepository;
    private final Checkout_Summery_Service checkout_Summery_Service;

    public Checkout_Summery_Controller(AuthRepository authRepository,Checkout_Summery_Service checkout_Summery_Service) {
        this.authRepository = authRepository;
        this.checkout_Summery_Service = checkout_Summery_Service;
    }


    // Checkout Summary
    @GetMapping("/view/{userId}")
    public ResponseEntity<Checkout_Summery_response_DTO> checkoutSummary(@PathVariable final Long userId,Authentication authentication) {

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!loggedInUser.getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }

        Checkout_Summery_response_DTO response = checkout_Summery_Service.checkoutSummary(userId);

        return ResponseEntity.ok(response);
    }

}
