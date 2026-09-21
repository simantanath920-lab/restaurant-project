package com.simanta.restaurant_backend.controller;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.simanta.restaurant_backend.dto.PageResponse;
import com.simanta.restaurant_backend.dto.USER_OrderPage_CurrentOrder_response_DTO;
import com.simanta.restaurant_backend.dto.USER_OrderPage_orderTable_response_DTO;
import com.simanta.restaurant_backend.service.USER_OrderPage_Service;
 
@RestController
@RequestMapping("/restaurant/user/homepage")
public class USER_OrderPage_Controller {
 
    private final USER_OrderPage_Service orderPage_Service;

    public USER_OrderPage_Controller(USER_OrderPage_Service orderPage_Service){
        this.orderPage_Service = orderPage_Service;
    }
 
    @GetMapping("/orderViewPanel")
    public ResponseEntity<PageResponse<USER_OrderPage_orderTable_response_DTO>> userMainPageOrder(Pageable pageable,Authentication authentication){

        final String email = authentication.getName();

        PageResponse<USER_OrderPage_orderTable_response_DTO> userMainPageOrderResponse = orderPage_Service.userOrderMainPageAllOrder(email,pageable);

        return ResponseEntity.status(HttpStatus.OK).body(userMainPageOrderResponse);
    }


    @GetMapping("/currentOrder/{userId}")
    public ResponseEntity<USER_OrderPage_CurrentOrder_response_DTO> userOrderMainPageCurrentOrder(@PathVariable final Long userId){

        USER_OrderPage_CurrentOrder_response_DTO currentOrder_response = orderPage_Service.userOrderMainPageCurrentOrder(userId);

        return ResponseEntity.status(HttpStatus.OK).body(currentOrder_response);
    }

}
