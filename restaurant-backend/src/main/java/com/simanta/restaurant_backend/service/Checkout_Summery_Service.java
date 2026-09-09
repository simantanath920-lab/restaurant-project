package com.simanta.restaurant_backend.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simanta.restaurant_backend.dto.Address_View_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Cart_View_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Checkout_Summery_response_DTO;
import com.simanta.restaurant_backend.exception.Checkout_Summery_Exception;
import com.simanta.restaurant_backend.model.Address;
import com.simanta.restaurant_backend.model.Cart;
import com.simanta.restaurant_backend.model.CartItem;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AddressRepository;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.repository.CartRepository;

@Service
public class Checkout_Summery_Service {

    private final AuthRepository authRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;

    public Checkout_Summery_Service(AuthRepository authRepository,CartRepository cartRepository,AddressRepository addressRepository) {
        this.authRepository = authRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public Checkout_Summery_response_DTO checkoutSummary(Long userId) {

        User user = authRepository.findById(userId)
                .orElseThrow(() -> new Checkout_Summery_Exception("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new Checkout_Summery_Exception("Cart not found"));

        Address address = addressRepository.findByUserAndIsDefaultTrue(user)
                .orElseThrow(() -> new Checkout_Summery_Exception("Default address not found"));

        Checkout_Summery_response_DTO response = new Checkout_Summery_response_DTO();

        List<Cart_View_response_USER_DTO> items = new ArrayList<>();

        double cartTotal = 0;

        for (CartItem cartItem : cart.getCartItem()) {

            double subtotal = cartItem.getMenu().getPrice() * cartItem.getQuantity();

            Cart_View_response_USER_DTO dto = new Cart_View_response_USER_DTO(cartItem.getMenu().getId(),cartItem.getMenu().getName(),
                            cartItem.getMenu().getImageUrl(),cartItem.getMenu().getPrice(),cartItem.getQuantity(),subtotal);

            items.add(dto);

            cartTotal += subtotal;
        }

        double deliveryCharge;

        if (cartTotal >= 500) {
            deliveryCharge = 0;
        } else {
            deliveryCharge = 40;   // Your delivery charge
        }

        response.setItems(items);

        response.setAddress(new Address_View_response_USER_DTO(address.getId(),address.getFullName(),address.getPhoneNumber(),address.getStreet(),
        address.getArea(),address.getCity(),address.getState(),address.getPincode(),address.getCountry(),address.isDefault(),address.getAddressType()));

        response.setCartTotal(cartTotal);
        response.setDeliveryCharge(deliveryCharge);
        response.setGrandTotal(cartTotal + deliveryCharge);
        response.setFreeDelivery(cartTotal >= 500);

        return response;
    }

}
