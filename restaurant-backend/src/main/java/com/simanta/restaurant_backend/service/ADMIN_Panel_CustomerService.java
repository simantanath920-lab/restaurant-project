package com.simanta.restaurant_backend.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.simanta.restaurant_backend.dto.ADMIN_Customer_Panel_DTO_response;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.repository.OrderRepository;

@Service
public class ADMIN_Panel_CustomerService {

    private final AuthRepository authRepository;
    private final OrderRepository orderRepository;

    public ADMIN_Panel_CustomerService(AuthRepository authRepository,OrderRepository orderRepository){
        this.authRepository = authRepository;
        this.orderRepository = orderRepository;
    }

    // Get All Order
    public List<ADMIN_Customer_Panel_DTO_response> getAllUser(){

        List<User> users = authRepository.findAll();

        List<ADMIN_Customer_Panel_DTO_response> responseAllUsers = new ArrayList<>();

        for(User user:users){

            responseAllUsers.add(new ADMIN_Customer_Panel_DTO_response(user.getName(), 
                user.getEmail(), 
                user.getPhonenumber(), 
                user.getIsActive(), 
                user.getCreatedAt(), 
                orderRepository.countByUserId(user.getId())));
        }

        return responseAllUsers;
    }
}
