package com.simanta.restaurant_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.ADMIN_Orders_Panel_DTO;
import com.simanta.restaurant_backend.dto.PageResponse;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.Payment;
import com.simanta.restaurant_backend.repository.OrderRepository;
import com.simanta.restaurant_backend.repository.PaymentRepository;

@Service
public class ADMIN_Panel_OrdersService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public ADMIN_Panel_OrdersService(OrderRepository orderRepository,PaymentRepository paymentRepository){
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    // Admin Order panel
    public PageResponse<ADMIN_Orders_Panel_DTO> getAllOrders(Pageable pageable){

        Page<Order> orders = orderRepository.findAll(pageable);

        Page<ADMIN_Orders_Panel_DTO> dtoPage = orders.map(order -> {

             Payment payment = paymentRepository.findByOrderId(order.getId())
                .orElse(null);

            return new ADMIN_Orders_Panel_DTO(order.getId(),
                
                order.getTotalprice(), 
                
                payment != null 
                    ? payment.getPaymentMethod()
                    : null, 
                
                payment != null
                    ? payment.getPaymentStatus()
                    : null,
                
                order.getOrderStatus(),
                
                order.getCreatedAt(), 
                
                order.getUser().getId(),
                
                order.getSelectdefaultaddress().getId());
        });

        return new PageResponse<>(dtoPage.getContent(), dtoPage.getNumber(), dtoPage.getTotalPages(), dtoPage.getNumberOfElements(), dtoPage.getSize());
    }

}
