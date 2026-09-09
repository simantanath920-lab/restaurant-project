package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.simanta.restaurant_backend.dto.Cart_DELETE_response_DTO;
import com.simanta.restaurant_backend.dto.Cart_DTO_request_USER;
import com.simanta.restaurant_backend.dto.Cart_DTO_response_USER;
import com.simanta.restaurant_backend.dto.Cart_UPDATE_request_DTO;
import com.simanta.restaurant_backend.dto.Cart_UPDATE_response_DTO;
import com.simanta.restaurant_backend.dto.View_CART_response_DTO;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.service.CartService_USER;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/user/api/cart")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CartController {

    private final CartService_USER cartService_USER;
    private final AuthRepository authRepository;

    public CartController(CartService_USER cartService_USER,AuthRepository authRepository) {
        this.cartService_USER = cartService_USER;
        this.authRepository = authRepository;
    }

    // Add to Cart
    @PostMapping("/add/{id}")
    public ResponseEntity<Cart_DTO_response_USER> addCart(
            @PathVariable Long id,
            Authentication authentication,
            @Valid @RequestBody Cart_DTO_request_USER cart_DTO_request_USER) {

        String email = authentication.getName();

        User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!loggedInUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        Cart_DTO_response_USER cart_DTO_response = cartService_USER.addToCart(id, cart_DTO_request_USER);

        return ResponseEntity.status(HttpStatus.CREATED).body(cart_DTO_response);
    }

    // Update Cart
    @PutMapping("/update/{id}")
    public ResponseEntity<Cart_UPDATE_response_DTO> updateCart(@PathVariable Long id,Authentication authentication,@Valid @RequestBody Cart_UPDATE_request_DTO cart_UPDATE_request_DTO){

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Cart_UPDATE_response_DTO cart_UPDATE_response = cartService_USER.updateCart(id, cart_UPDATE_request_DTO);

        return ResponseEntity.status(HttpStatus.OK).body(cart_UPDATE_response);
    }
 
    // View Cart
    @GetMapping("/view/{id}")
    public ResponseEntity<View_CART_response_DTO> viewCart(@PathVariable Long id,Authentication authentication){

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        View_CART_response_DTO response_DTO = cartService_USER.viewCart(id);
        
        return ResponseEntity.status(HttpStatus.OK).body(response_DTO);
    }
 

    // Delete Cart
    @DeleteMapping("/delete/{userid}/{cartId}")
    public ResponseEntity<Cart_DELETE_response_DTO> deleteCart(@PathVariable Long userid,Authentication authentication,@PathVariable Long cartId){

        String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userid)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Cart_DELETE_response_DTO deleteResponse = cartService_USER.deleteCart(userid, cartId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
    }

}
