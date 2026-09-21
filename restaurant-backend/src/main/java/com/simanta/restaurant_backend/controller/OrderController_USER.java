package com.simanta.restaurant_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.simanta.restaurant_backend.dto.Order_Cancel_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Order_Get_All_By_User_response_DTO;
import com.simanta.restaurant_backend.dto.Order_Get_By_Id_response_DTO;
import com.simanta.restaurant_backend.dto.Order_PostOrder_request_USER_DTO;
import com.simanta.restaurant_backend.dto.Order_PostOrder_response_USER_DTO;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.service.OrderService_USER;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/user/api/order")
public class OrderController_USER {

    private final OrderService_USER orderService;
    private final AuthRepository authRepository;

    public OrderController_USER(OrderService_USER orderService,AuthRepository authRepository) {
        this.orderService = orderService;
        this.authRepository = authRepository;
    }

    // Placed order
    @PostMapping("/placed-order/{userId}")
    public ResponseEntity<Order_PostOrder_response_USER_DTO> order_PostOrder_response_USER_DTO(@PathVariable Long userId,@RequestBody @Valid Order_PostOrder_request_USER_DTO order_PostOrder_request_USER_DTO,
        Authentication authentication) throws MessagingException{

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Order_PostOrder_response_USER_DTO order_PostOrder_response = orderService.placedOrder(loggedInUser,order_PostOrder_request_USER_DTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(order_PostOrder_response);
    }


    // Get All orders
    @GetMapping("/get-order-by-user/{userId}")
    public ResponseEntity<Order_Get_All_By_User_response_DTO> get_All_Order(@PathVariable final Long userId,Authentication authentication){

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Order_Get_All_By_User_response_DTO View_All_response_DTO = orderService.get_All_Order(email);

        return ResponseEntity.status(HttpStatus.OK).body(View_All_response_DTO);
    }


    // Get Order by id
    @GetMapping("/get-order-by-id/{userId}/{orderId}")
    public ResponseEntity<Order_Get_By_Id_response_DTO> get_Order_by_id(@PathVariable final Long userId,@PathVariable final Long orderId,Authentication authentication){

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Order_Get_By_Id_response_DTO get_By_id_response_DTO = orderService.get_order_By_id(orderId);

        return ResponseEntity.status(HttpStatus.OK).body(get_By_id_response_DTO);
    } 
 
    // Cancel Order
    @PatchMapping("/cancel-order/{userId}/{orderId}")
    public ResponseEntity<Order_Cancel_response_USER_DTO> cancel_order(@PathVariable final Long userId,@PathVariable final Long orderId,Authentication authentication) throws MessagingException{

        final String email = authentication.getName();

        final User loggedInUser = authRepository.findByEmail(email)
            .orElseThrow(()-> new RuntimeException("User not found"));

        if(!loggedInUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }

        Order_Cancel_response_USER_DTO order_Cancel_response = orderService.cancel_order(orderId,userId);

        return ResponseEntity.status(HttpStatus.OK).body(order_Cancel_response);
    }

}
