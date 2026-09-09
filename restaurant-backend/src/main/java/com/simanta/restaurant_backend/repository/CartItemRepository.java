package com.simanta.restaurant_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simanta.restaurant_backend.model.Cart;
import com.simanta.restaurant_backend.model.CartItem;
import com.simanta.restaurant_backend.model.Menu;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    Optional<CartItem> findByCartAndMenu(Cart cart,Menu menu);

    List<CartItem> findByCart(Cart cart);
    List<CartItem> findByCartId(Long cartid);

}
