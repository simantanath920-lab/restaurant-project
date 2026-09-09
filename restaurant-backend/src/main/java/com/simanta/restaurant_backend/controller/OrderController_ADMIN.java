package com.simanta.restaurant_backend.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.Order_Status_Update_request_ADMIN_DTO;
import com.simanta.restaurant_backend.dto.Order_Status_Update_response_ADMIN_DTO;
import com.simanta.restaurant_backend.dto.Order_Table_orderstatus_AND_paymentstatus_response_DTO;
import com.simanta.restaurant_backend.dto.PageResponse;
import com.simanta.restaurant_backend.dto.Payment_Status_COD_request_ADMIN_DTO;
import com.simanta.restaurant_backend.dto.Payment_Status_COD_response_ADMIN_DTO;
import com.simanta.restaurant_backend.service.OrderService_ADMIN;
 
@RestController
@RequestMapping("/restaurant/user/api/order/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OrderController_ADMIN { 

    private final OrderService_ADMIN orderService_ADMIN; 

    public OrderController_ADMIN(OrderService_ADMIN orderService_ADMIN) {
        this.orderService_ADMIN = orderService_ADMIN;
    }
  
    @PatchMapping("/updateOrder-status/{userId}")
    public ResponseEntity<Order_Status_Update_response_ADMIN_DTO> update_status(@PathVariable final Long userId,
        @RequestBody final Order_Status_Update_request_ADMIN_DTO order_Status_Update_request_ADMIN_DTO){

        Order_Status_Update_response_ADMIN_DTO order_Status_Update_response = orderService_ADMIN.update_order_status(userId, order_Status_Update_request_ADMIN_DTO);
            
        return ResponseEntity.status(HttpStatus.OK).body(order_Status_Update_response);
    }


    @PatchMapping("/updatepayment-status/{userId}")
    public ResponseEntity<Payment_Status_COD_response_ADMIN_DTO> payment_COD_status(@PathVariable final Long userId,
        @RequestBody final Payment_Status_COD_request_ADMIN_DTO payment_Status_COD_request_ADMIN_DTO){

        Payment_Status_COD_response_ADMIN_DTO payment_Status_COD_response = orderService_ADMIN.payment_COD_status(userId, payment_Status_COD_request_ADMIN_DTO);
        
        return ResponseEntity.status(HttpStatus.OK).body(payment_Status_COD_response);
    }


    @GetMapping("/orderstatuspaymentstatus/{adminId}")
    public ResponseEntity<PageResponse<Order_Table_orderstatus_AND_paymentstatus_response_DTO>> orderstatus_AND_paymentstatus(@PathVariable final Long adminId,Pageable pageable){

        PageResponse<Order_Table_orderstatus_AND_paymentstatus_response_DTO> orderstatuspaymentstatus = orderService_ADMIN.orderstatus_AND_paymentstatus(adminId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(orderstatuspaymentstatus);
    }


}
