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
import com.simanta.restaurant_backend.model.Cart;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.Payment;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;
import com.simanta.restaurant_backend.repository.CartRepository;
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

    private final CartRepository cartRepository;

    private final Message_SendingInEmail_for_Updates_Service message_SendingInEmail_for_Updates_Service;

    private final OwnerNotificationService ownerNotificationService;


    public PaymentService_USER(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            CartRepository cartRepository,
            Message_SendingInEmail_for_Updates_Service message_SendingInEmail_for_Updates_Service,
            OwnerNotificationService ownerNotificationService) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.message_SendingInEmail_for_Updates_Service =
                message_SendingInEmail_for_Updates_Service;
        this.ownerNotificationService =
                ownerNotificationService;
    }


    // =========================
    // CREATE PAYMENT
    // =========================

    @Transactional
    public Razorpay_CreatePayment_response_Service_DTO create_payment(
            final Long userid,
            final Long orderid) throws RazorpayException {

        final Order order = orderRepository.findById(orderid)
                .orElseThrow(() ->
                        new PaymentService_USER_Exception("Order not found"));


        if (!order.getUser().getId().equals(userid)) {

            throw new PaymentService_USER_Exception("User not found");
        }


        if (order.getOrderStatus() == OrderStatus.CANCELLED) {

            throw new PaymentService_USER_Exception(
                    "This order has been cancelled."
            );
        }


        if (order.getOrderStatus() == OrderStatus.CONFIRMED) {

            throw new PaymentService_USER_Exception(
                    "This order has already been confirmed."
            );
        }


        if (order.getTotalprice() < 1) {

            throw new PaymentService_USER_Exception(
                    "Transaction cannot be less than 1"
            );
        }


        // =========================
        // CREATE RAZORPAY CLIENT
        // =========================

        RazorpayClient client =
                new RazorpayClient(
                        keyId,
                        keySecret
                );


        // Convert rupees to paise

        long amountInPaisa =
                Math.round(
                        order.getTotalprice() * 100
                );


        // =========================
        // CREATE RAZORPAY ORDER
        // =========================

        JSONObject razorpayOrderRequest =
                new JSONObject();

        razorpayOrderRequest.put(
                "amount",
                amountInPaisa
        );

        razorpayOrderRequest.put(
                "currency",
                "INR"
        );

        razorpayOrderRequest.put(
                "receipt",
                "order_" + order.getId()
        );


        com.razorpay.Order razorOrder =
                client.orders.create(
                        razorpayOrderRequest
                );


        String razorpayOrderId =
                razorOrder.get("id").toString();


        // =========================
        // SAVE / REUSE PAYMENT
        // =========================

        Payment payment =
                paymentRepository.findByOrderId(order.getId())
                        .orElse(null);


        /*
         * A restaurant Order can have only one Payment
         * because Payment.order is @OneToOne.
         *
         * Therefore, when the customer closes Razorpay
         * and tries again, do NOT create another Payment row.
         *
         * Reuse the existing Payment record and replace
         * the Razorpay order information.
         */

        if (payment == null) {

            payment = new Payment();

            payment.setOrder(order);

        }


        payment.setTotalAmount(
                order.getTotalprice()
        );

        payment.setPaymentMethod(
                PaymentMethod.ONLINE
        );

        payment.setPaymentStatus(
                PaymentStatus.CREATED
        );

        payment.setRazorpayOrderId(
                razorpayOrderId
        );

        /*
         * These belong to the previous Razorpay attempt.
         * Clear them before creating a new attempt.
         */

        payment.setRazorpayPaymentId(null);

        payment.setRazorpaySignature(null);

        paymentRepository.save(payment);


        // =========================
        // SEND DATA TO FRONTEND
        // =========================

        return new Razorpay_CreatePayment_response_Service_DTO(
                order.getId(),
                order.getTotalprice(),
                PaymentStatus.CREATED,
                razorpayOrderId,
                keyId,
                amountInPaisa
        );
    }


    // =========================
    // VERIFY PAYMENT
    // =========================

    @Transactional
    public Razorpay_VerifyPaymen_response_Service_DTO verify_payment(
            final String razorpayOrderId,
            final String razorpayPaymentId,
            final String razorpaySignature)
            throws RazorpayException {


        final Payment payment =
                paymentRepository
                        .findByRazorpayOrderId(
                                razorpayOrderId
                        )
                        .orElseThrow(() ->
                                new PaymentService_USER_Exception(
                                        "Payment not found"
                                ));


        final Order order =
                payment.getOrder();


        if (order == null) {

            throw new PaymentService_USER_Exception(
                    "Order not found for payment"
            );
        }


        // =========================
        // ALREADY SUCCESSFUL
        // =========================

        if (payment.getPaymentStatus()
                == PaymentStatus.SUCCESS) {

            return new Razorpay_VerifyPaymen_response_Service_DTO(
                    "Payment successful! Your order is confirmed"
            );
        }


        // =========================
        // VERIFY SIGNATURE
        // =========================

        String generatedSignature =
                Utils.getHash(
                        razorpayOrderId
                                + "|"
                                + razorpayPaymentId,
                        keySecret
                );


        // =========================
        // SIGNATURE FAILED
        // =========================

        if (!generatedSignature.equals(
                razorpaySignature)) {

            payment.setPaymentStatus(
                    PaymentStatus.FAILED
            );

            paymentRepository.save(payment);

            return new Razorpay_VerifyPaymen_response_Service_DTO(
                    "Payment failed! Please try again"
            );
        }


        // =========================
        // SUCCESSFUL PAYMENT
        // =========================

        payment.setRazorpayPaymentId(
                razorpayPaymentId
        );

        payment.setRazorpaySignature(
                razorpaySignature
        );

        payment.setPaymentStatus(
                PaymentStatus.SUCCESS
        );


        order.setOrderStatus(
                OrderStatus.CONFIRMED
        );

        orderRepository.save(order);

        paymentRepository.save(payment);


        // =========================
        // CLEAR CART
        // =========================

        /*
         * IMPORTANT:
         *
         * The cart is cleared ONLY here,
         * after Razorpay verification succeeds.
         */

        final Cart cart =
                cartRepository.findByUser(
                        order.getUser()
                ).orElse(null);


        if (cart != null
                && cart.getCartItem() != null) {

            cart.getCartItem().clear();

            cartRepository.save(cart);
        }


        // =========================
        // CUSTOMER NOTIFICATION
        // =========================

        message_SendingInEmail_for_Updates_Service
                .Sending_Message_for_Notification(
                        order.getUser().getEmail(),
                        order.getId()
                );


        // =========================
        // OWNER NOTIFICATION
        // =========================

        ownerNotificationService
                .Sending_Message_for_OwnerNotification(
                        ownerEmail,
                        order,
                        payment.getPaymentMethod()
                );


        return new Razorpay_VerifyPaymen_response_Service_DTO(
                "Payment successful! Your order is confirmed"
        );
    }
}