package com.simanta.restaurant_backend.controller;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.ADMIN_Orders_Panel_DTO;
import com.simanta.restaurant_backend.dto.PageResponse;
import com.simanta.restaurant_backend.service.ADMIN_Panel_OrdersService;

@RestController
@RequestMapping("/restaurant/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ADMIN_Panel_OrdersController {

    private final ADMIN_Panel_OrdersService admin_Panel_OrdersService;

    public ADMIN_Panel_OrdersController(ADMIN_Panel_OrdersService admin_Panel_OrdersService){
        this.admin_Panel_OrdersService = admin_Panel_OrdersService;
    }

    @GetMapping("/orders")
    public ResponseEntity<PageResponse<ADMIN_Orders_Panel_DTO>> getAllOrders(Pageable pageable){

        PageResponse<ADMIN_Orders_Panel_DTO> orders_Panel_response = admin_Panel_OrdersService.getAllOrders(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(orders_Panel_response);
    }
}
