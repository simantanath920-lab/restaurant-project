package com.simanta.restaurant_backend.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.OrderItem;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.Payment;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.dto.PageResponse;
import com.simanta.restaurant_backend.dto.USER_OrderPage_CurrentOrder_response_DTO;
import com.simanta.restaurant_backend.dto.USER_OrderPage_orderTable_response_DTO;
import com.simanta.restaurant_backend.dto.USER_OrderPage_orderitemTable_response_DTO;
import com.simanta.restaurant_backend.exception.EmailDoesNotExistException;
import com.simanta.restaurant_backend.exception.USER_OrderPage_Service_Exception;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.repository.OrderItemRepository;
import com.simanta.restaurant_backend.repository.OrderRepository;
import com.simanta.restaurant_backend.repository.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class USER_OrderPage_Service {
 
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final AuthRepository authRepository;

    public USER_OrderPage_Service(OrderRepository orderRepository,OrderItemRepository orderItemRepository,PaymentRepository paymentRepository,AuthRepository authRepository){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.authRepository = authRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<USER_OrderPage_orderTable_response_DTO> userOrderMainPageAllOrder(final String email,Pageable pageable){

        final User user =  authRepository.findByEmail(email)
            .orElseThrow(()-> new USER_OrderPage_Service_Exception("User not found"));

        Page<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId(),pageable);

        Page<USER_OrderPage_orderTable_response_DTO> dtoResponse = orders.map(order ->{

            Payment payment = paymentRepository.findByOrderId(order.getId())
                .orElse(null);





            List<OrderItem> orderitems = orderItemRepository.findByOrderId(order.getId());

            List<USER_OrderPage_orderitemTable_response_DTO> orderitemTable_response = new ArrayList<>();

            for(OrderItem orderitem : orderitems){

                USER_OrderPage_orderitemTable_response_DTO dto = new USER_OrderPage_orderitemTable_response_DTO(orderitem.getMenu().getName(), orderitem.getQuantity(), orderitem.getPrice() * orderitem.getQuantity());

                orderitemTable_response.add(dto);
            }




            

            return new USER_OrderPage_orderTable_response_DTO(order.getId(), 
                order.getCreatedAt(), 
                order.getOrderStatus(), 

                orderitemTable_response,

                 payment != null
                    ? payment.getPaymentStatus()
                    : null, 

                 payment != null
                    ? payment.getPaymentMethod()
                    : null,

                  order.getSelectdefaultaddress().getCity(), 
                  order.getSelectdefaultaddress().getArea(), 
                  order.getSelectdefaultaddress().getStreet(), 
                  order.getSelectdefaultaddress().getPhoneNumber());
            });

        return new PageResponse<>(dtoResponse.getContent(), dtoResponse.getNumber(), dtoResponse.getTotalPages(), dtoResponse.getTotalElements(), dtoResponse.getSize());
    }



    // Current Order
    @Transactional(readOnly = true)
    public USER_OrderPage_CurrentOrder_response_DTO userOrderMainPageCurrentOrder(final Long userId){

        final Order currentOrder = orderRepository.findFirstByUserIdAndOrderStatusNotOrderByIdDesc(userId,OrderStatus.CANCELLED)
            .orElseThrow(()-> new EmailDoesNotExistException("Current Order not available"));

            USER_OrderPage_CurrentOrder_response_DTO currentOrder_response = new USER_OrderPage_CurrentOrder_response_DTO();

        List<OrderItem> currentOrder_Orderitems = orderItemRepository.findByOrderId(currentOrder.getId());

        List<USER_OrderPage_orderitemTable_response_DTO> currentOrderitemsStorage = new ArrayList<>();

        for(OrderItem orderitem : currentOrder_Orderitems){

            USER_OrderPage_orderitemTable_response_DTO orderPage_orderitemTable_response = new USER_OrderPage_orderitemTable_response_DTO
                    (orderitem.getMenu().getName(), orderitem.getQuantity(), orderitem.getPrice() * orderitem.getQuantity());

            currentOrderitemsStorage.add(orderPage_orderitemTable_response);
         }

         currentOrder_response.setOrderid(currentOrder.getId());
         currentOrder_response.setCreatedAt(currentOrder.getCreatedAt());
         currentOrder_response.setOrderStatus(currentOrder.getOrderStatus());
         currentOrder_response.setOrderitems(currentOrderitemsStorage);
         currentOrder_response.setCity(currentOrder.getSelectdefaultaddress().getCity());
         currentOrder_response.setArea(currentOrder.getSelectdefaultaddress().getArea());
         currentOrder_response.setStreet(currentOrder.getSelectdefaultaddress().getStreet());
         currentOrder_response.setPhoneNumber(currentOrder.getSelectdefaultaddress().getPhoneNumber());

         final Payment payment = paymentRepository.findByOrderId(currentOrder.getId())
            .orElse(null);

            if(payment != null){
                currentOrder_response.setPaymentStatus(payment.getPaymentStatus());
                currentOrder_response.setPaymentMethod(payment.getPaymentMethod());
            }

        return currentOrder_response;
    }
    
}
