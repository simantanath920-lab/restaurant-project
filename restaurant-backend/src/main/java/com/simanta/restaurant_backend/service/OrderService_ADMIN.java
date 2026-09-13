package com.simanta.restaurant_backend.service;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import com.simanta.restaurant_backend.dto.Order_Status_Update_request_ADMIN_DTO;
import com.simanta.restaurant_backend.dto.Order_Status_Update_response_ADMIN_DTO;
import com.simanta.restaurant_backend.dto.Order_Table_orderstatus_AND_paymentstatus_response_DTO;
import com.simanta.restaurant_backend.dto.PageResponse;
import com.simanta.restaurant_backend.dto.Payment_Status_COD_request_ADMIN_DTO;
import com.simanta.restaurant_backend.dto.Payment_Status_COD_response_ADMIN_DTO;
import com.simanta.restaurant_backend.exception.OrderService_ADMIN_Exception;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.Payment;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.repository.OrderRepository;
import com.simanta.restaurant_backend.repository.PaymentRepository;
  
@Service
public class OrderService_ADMIN {

    private final OrderRepository orderRepository;
    private final AuthRepository authRepository;
    private final PaymentRepository paymentRepository;

    public OrderService_ADMIN(OrderRepository orderRepository,AuthRepository authRepository,PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.authRepository = authRepository;
        this.paymentRepository = paymentRepository;
    }
  
    // Update Order Status
    public Order_Status_Update_response_ADMIN_DTO update_order_status(final Long userid,final Order_Status_Update_request_ADMIN_DTO order_Status_Update_request_ADMIN_DTO){

        final User admin = authRepository.findById(userid)
            .orElseThrow(()-> new OrderService_ADMIN_Exception("Admin not found"));

            if(!"ADMIN".equals(admin.getRole())){
                throw new OrderService_ADMIN_Exception("Access denied for this profile.");
            }

        final Order order = orderRepository.findById(order_Status_Update_request_ADMIN_DTO.getId())
                .orElseThrow(()-> new OrderService_ADMIN_Exception("order not found"));

        order.setOrderStatus(order_Status_Update_request_ADMIN_DTO.getOrderStatus());

        orderRepository.save(order);

        return new Order_Status_Update_response_ADMIN_DTO("Order status changed to " + order.getOrderStatus());
    }



    // Paymany COD success 
    public Payment_Status_COD_response_ADMIN_DTO payment_COD_status(final Long userid,final Payment_Status_COD_request_ADMIN_DTO payment_Status_COD_request_ADMIN_DTO){

        final User admin = authRepository.findById(userid)
            .orElseThrow(()-> new OrderService_ADMIN_Exception("Admin not found"));

            if(!"ADMIN".equals(admin.getRole())){
                throw new OrderService_ADMIN_Exception("Access denied for this profile.");
            }

        final Payment payment = paymentRepository.findById(payment_Status_COD_request_ADMIN_DTO.getId())
            .orElseThrow(()-> new OrderService_ADMIN_Exception("Payment not found"));

            if(payment.getPaymentMethod() != PaymentMethod.COD){
                throw new OrderService_ADMIN_Exception("This payment is not a COD payment.");
            }

            if(payment.getPaymentStatus() == PaymentStatus.SUCCESS){
                throw new OrderService_ADMIN_Exception("COD payment is already successful.");
            }

            payment.setPaymentStatus(payment_Status_COD_request_ADMIN_DTO.getPaymentStatus());

            paymentRepository.save(payment);

        return new Payment_Status_COD_response_ADMIN_DTO("COD status updated to " + payment.getPaymentStatus());
    }


 

    // Order status AND Payment Status
    @Transactional(readOnly = true)
    public PageResponse<Order_Table_orderstatus_AND_paymentstatus_response_DTO> orderstatus_AND_paymentstatus(final Long adminId,Pageable pageable){

        final User admin = authRepository.findById(adminId)
            .orElseThrow(()-> new OrderService_ADMIN_Exception("Admin not found"));

        if(!"ADMIN".equals(admin.getRole())){
            throw new OrderService_ADMIN_Exception("Access Denied for this profile.");
        }

        Page<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc(pageable);

        Page<Order_Table_orderstatus_AND_paymentstatus_response_DTO> Response = orders.map(order -> {

            Payment payment = paymentRepository.findByOrderId(order.getId())
                    .orElse(null);

            return new Order_Table_orderstatus_AND_paymentstatus_response_DTO(
                                order.getId(),
                                order.getUser().getId(),
                                payment != null
                                        ? payment.getId()
                                        : null,
                                order.getTotalprice(),
 
                                payment != null
                                        ? payment.getPaymentMethod()
                                        : null,

                                payment != null
                                        ? payment.getPaymentStatus()
                                        : null,

                                order.getOrderStatus(),
                                order.getCreatedAt()
                        );
                    });

            return new PageResponse<>(Response.getContent(), Response.getNumber(), Response.getTotalPages(), Response.getTotalElements(),Response.getSize());
    }

}
