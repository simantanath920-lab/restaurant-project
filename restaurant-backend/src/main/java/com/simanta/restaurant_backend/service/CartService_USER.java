package com.simanta.restaurant_backend.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.simanta.restaurant_backend.dto.Cart_DELETE_response_DTO;
import com.simanta.restaurant_backend.dto.Cart_DTO_request_USER;
import com.simanta.restaurant_backend.dto.Cart_DTO_response_USER;
import com.simanta.restaurant_backend.dto.Cart_UPDATE_request_DTO;
import com.simanta.restaurant_backend.dto.Cart_UPDATE_response_DTO;
import com.simanta.restaurant_backend.dto.View_CARTITEM_response_DTO;
import com.simanta.restaurant_backend.dto.View_CART_response_DTO;
import com.simanta.restaurant_backend.exception.CartService_USER_Exception;
import com.simanta.restaurant_backend.model.Cart;
import com.simanta.restaurant_backend.model.CartItem;
import com.simanta.restaurant_backend.model.Menu;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.repository.CartItemRepository;
import com.simanta.restaurant_backend.repository.CartRepository;
import com.simanta.restaurant_backend.repository.MenuRepository;
import jakarta.transaction.Transactional;

@Service
public class CartService_USER {

    private final AuthRepository authRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuRepository menuRepository;

    public CartService_USER(CartRepository cartRepository,CartItemRepository cartItemRepository,AuthRepository authRepository,MenuRepository menuRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.authRepository = authRepository;
        this.menuRepository = menuRepository;
    }

    
    // Add To Cart
    @Transactional
    public Cart_DTO_response_USER addToCart(final Long id,final Cart_DTO_request_USER cart_DTO_request_USER){
 
        final User user = authRepository.findById(id)
        .orElseThrow(()-> new CartService_USER_Exception("User not found"));

        final Cart cart = cartRepository.findByUser(user)
          .orElseGet(()-> {
                final Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });

        Menu menu = menuRepository.findById(cart_DTO_request_USER.getMenuid())
                .orElseThrow(()-> new CartService_USER_Exception("Menu not found"));

        Optional<CartItem> existing_CartItem = cartItemRepository.findByCartAndMenu(cart, menu);

        if(existing_CartItem.isPresent()){
           CartItem cartItem = existing_CartItem.get();
           cartItem.setQuantity(cartItem.getQuantity() + cart_DTO_request_USER.getQuantity());
           cartItemRepository.save(cartItem);
        }
        else{
           CartItem cartItem = new CartItem();
           cartItem.setCart(cart);
           cartItem.setMenu(menu);
           cartItem.setQuantity(cart_DTO_request_USER.getQuantity());
           cartItemRepository.save(cartItem);
       }

        return new Cart_DTO_response_USER("Item added to cart");

    }

    

    // Update Cart
    @Transactional
    public Cart_UPDATE_response_DTO updateCart(final Long id,final Cart_UPDATE_request_DTO cart_UPDATE_request_DTO){


        final User user = authRepository.findById(id)
            .orElseThrow(()-> new CartService_USER_Exception("User not found"));

        final Cart cart = cartRepository.findByUser(user)
            .orElseThrow(()-> new CartService_USER_Exception("Cart not found"));

        final Menu menu = menuRepository.findById(cart_UPDATE_request_DTO.getMenuid())
            .orElseThrow(()-> new CartService_USER_Exception("Menu not found"));

        final CartItem cartItem = cartItemRepository.findByCartAndMenu(cart, menu)
            .orElseThrow(()-> new CartService_USER_Exception("Item not in cart"));

            if (cart_UPDATE_request_DTO.getQuantity() == 1) {

                int newQuantity = cartItem.getQuantity() + 1;

                if(newQuantity > menu.getStock()){
                    throw new CartService_USER_Exception("Insufficient stock. Only " + menu.getStock() + " item(s) available.");
                }

                cartItem.setQuantity(newQuantity);
            } 

            else if (cart_UPDATE_request_DTO.getQuantity() == -1) {
                if (cartItem.getQuantity() == 1) {
                    cartItemRepository.delete(cartItem);
                    return new Cart_UPDATE_response_DTO("Item removed from cart");
                }
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            }

        cartItemRepository.save(cartItem);
        return new Cart_UPDATE_response_DTO("Item updated.");
    }
 



    // Get Cart
    public View_CART_response_DTO viewCart(Long id){

        final User user = authRepository.findById(id)
            .orElseThrow(()-> new CartService_USER_Exception("User not found"));

        final Cart cart = cartRepository.findByUser(user)
            .orElseGet(()-> {

                Cart newCart = new Cart();
                newCart.setUser(user);

                return cartRepository.save(newCart);
            });

        List<View_CARTITEM_response_DTO> items = new ArrayList<>();

        double totalprice = 0;

        for(CartItem item : cart.getCartItem()){

            View_CARTITEM_response_DTO viewCartitem = new View_CARTITEM_response_DTO(
            item.getMenu().getId(),
            item.getMenu().getName(),
            item.getQuantity(),
            item.getMenu().getImageUrl(),
            item.getMenu().getPrice(),
            item.getQuantity() * item.getMenu().getPrice());

            items.add(viewCartitem);
            totalprice += viewCartitem.getSubTotal();
        }
        
        return new View_CART_response_DTO(cart.getId(),items,totalprice);
    }                                                        
 


    // Delete Cart 
    @Transactional
    public Cart_DELETE_response_DTO deleteCart(final Long userid,final Long cartId){


        final User user = authRepository.findById(userid)
            .orElseThrow(()-> new CartService_USER_Exception("User not found"));

        cartRepository.findByUser(user)
            .orElseThrow(()-> new CartService_USER_Exception("Cart not found"));

        List<CartItem> cartitems = cartItemRepository.findByCartId(cartId);

        for(CartItem CARTITEM : cartitems){

            cartItemRepository.delete(CARTITEM);
        }
        
        return new Cart_DELETE_response_DTO("Item removed.");
    }

}
