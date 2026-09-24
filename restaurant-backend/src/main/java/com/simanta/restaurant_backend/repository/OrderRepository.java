package com.simanta.restaurant_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.User;
 
@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

    Optional<Order> findByUser(User user);
    long countByUserId(Long userId);
    List<Order> findByUserId(Long userId);

    
    Page<Order> findByUserId(Long userid,Pageable pageable);
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Order> findAllByOrderByIdDesc(Pageable pageable); 

    Page<Order> findByUserIdOrderByCreatedAtDescIdDesc(Long userId,Pageable pageable);
    Optional<Order> findFirstByUserIdAndOrderStatusNotOrderByCreatedAtDescIdDesc(Long userId,OrderStatus orderStatus);
    Page<Order> findByUserIdOrderByIdDesc(Long userId,Pageable pageable);
    
}
