package com.simanta.restaurant_backend.service;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.simanta.restaurant_backend.dto.Razorpay_CreatePayment_response_Service_DTO;
import com.simanta.restaurant_backend.dto.Razorpay_VerifyPaymen_response_Service_DTO;
import com.simanta.restaurant_backend.exception.PaymentService_USER_Exception;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.Payment;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;
import com.simanta.restaurant_backend.repository.OrderRepository;
import com.simanta.restaurant_backend.repository.PaymentRepository;
import jakarta.transaction.Transactional;

@Service
public class PaymentService_USER {

    @Value("${restaurant.owner.email}")
    private String ownerEmail;

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final Message_SendingInEmail_for_Updates_Service message_SendingInEmail_for_Updates_Service;
    private final OwnerNotificationService ownerNotificationService;

    public PaymentService_USER(PaymentRepository paymentRepository,OrderRepository orderRepository,Message_SendingInEmail_for_Updates_Service message_SendingInEmail_for_Updates_Service,OwnerNotificationService ownerNotificationService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.message_SendingInEmail_for_Updates_Service = message_SendingInEmail_for_Updates_Service;
        this.ownerNotificationService = ownerNotificationService;
    }

    // Create Payment
    @Transactional
    public Razorpay_CreatePayment_response_Service_DTO create_payment(final Long userid,final Long orderid) throws RazorpayException{

        final Order order = orderRepository.findById(orderid)
                .orElseThrow(()-> new PaymentService_USER_Exception("Order not found"));

        if(!order.getUser().getId().equals(userid)){
            throw new PaymentService_USER_Exception("User not found");
        }

        if(order.getTotalprice() < 1){
            throw new PaymentService_USER_Exception("Transaction cannot be less than 1");
        }

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        long amountInPaisa = Math.round(order.getTotalprice() * 100);

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("amount",amountInPaisa);
        jSONObject.put("currency","INR");
        jSONObject.put("receipt","order_" + order.getId());

        com.razorpay.Order razorOrder = client.orders.create(jSONObject);
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setTotalAmount(order.getTotalprice());
        payment.setPaymentMethod(PaymentMethod.ONLINE);
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setRazorpayOrderId(razorOrder.get("id").toString());

        paymentRepository.save(payment);
        
        return new Razorpay_CreatePayment_response_Service_DTO(order.getId(),order.getTotalprice(),PaymentStatus.CREATED,razorOrder.get("id").toString());
    }

 
    // Verify Payment
    @Transactional
    public Razorpay_VerifyPaymen_response_Service_DTO verify_payment(final String razorpayOrderId,final String razorpayPaymentId,final String razorpaySignature) 
                    throws RazorpayException{

        final Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId)
            .orElseThrow(()-> new PaymentService_USER_Exception("Payment not found"));

        String generatedSignature = Utils.getHash(razorpayOrderId + "|" + razorpayPaymentId , keySecret);

        if(!generatedSignature.equals(razorpaySignature)){
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            return new Razorpay_VerifyPaymen_response_Service_DTO("Payment failed! Please try again");
        }

        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setRazorpaySignature(razorpaySignature);

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.getOrder().setOrderStatus(OrderStatus.CONFIRMED);

        paymentRepository.save(payment);

        message_SendingInEmail_for_Updates_Service.Sending_Message_for_Notification(payment.getOrder().getUser().getEmail(),payment.getOrder().getId());

        ownerNotificationService.Sending_Message_for_OwnerNotification(ownerEmail,payment.getOrder(),payment.getPaymentMethod());

        return new Razorpay_VerifyPaymen_response_Service_DTO("Payment successful! Your order is confirmed");
    }
}
