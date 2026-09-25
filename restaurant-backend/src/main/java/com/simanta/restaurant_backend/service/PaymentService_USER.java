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
import com.simanta.restaurant_backend.model.CartItem;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.OrderItem;
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



    private void syncOrderWithCart(
        final Order order,
        final Cart cart) {

    if (cart.getCartItem() == null
            || cart.getCartItem().isEmpty()) {

        throw new PaymentService_USER_Exception(
                "Cart is empty"
        );
    }


    // Remove old order items

    order.getOrderitems().clear();


    double totalprice = 0;


    // Create order items from CURRENT cart

    for (CartItem cartItem : cart.getCartItem()) {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setMenu(cartItem.getMenu());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getMenu().getPrice());


        double total =
                cartItem.getQuantity()
                * cartItem.getMenu().getPrice();

        totalprice += total;


        order.getOrderitems().add(orderItem);
    }


    // Delivery charge

    double deliveryCharge =
            totalprice < 500 ? 40 : 0;


    double finaltotalprice =
            totalprice + deliveryCharge;


    order.setTotalprice(finaltotalprice);
}





    // =========================
    // CREATE PAYMENT
    // =========================

    @Transactional
public Razorpay_CreatePayment_response_Service_DTO create_payment(
        final Long userid,
        final Long orderid) throws RazorpayException {


    // =========================
    // FIND ORDER
    // =========================

    final Order order = orderRepository.findById(orderid)
            .orElseThrow(() ->
                    new PaymentService_USER_Exception(
                            "Order not found"
                    ));


    // =========================
    // CHECK USER
    // =========================

    if (!order.getUser().getId().equals(userid)) {

        throw new PaymentService_USER_Exception(
                "User not found"
        );
    }


    // =========================
    // CHECK ORDER STATUS
    // =========================

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


    // =========================
    // FIND CURRENT CART
    // =========================

    final Cart cart = cartRepository.findByUser(
            order.getUser()
    ).orElseThrow(() ->
            new PaymentService_USER_Exception(
                    "Cart not found"
            ));


    if (cart.getCartItem() == null
            || cart.getCartItem().isEmpty()) {

        throw new PaymentService_USER_Exception(
                "Cart is empty"
        );
    }


    // =========================
    // SYNC ORDER WITH CART
    // =========================

    /*
     * IMPORTANT:
     *
     * The customer may have changed
     * quantity after the restaurant Order
     * was originally created.
     *
     * Therefore the Order must always
     * be rebuilt from the CURRENT cart
     * before creating Razorpay payment.
     */

    syncOrderWithCart(order, cart);


    orderRepository.save(order);


    // =========================
    // CHECK TOTAL
    // =========================

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


    // =========================
    // CONVERT RUPEES TO PAISE
    // =========================

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


    final String razorpayOrderId =
            razorOrder.get("id").toString();


    // =========================
    // FIND EXISTING PAYMENT
    // =========================

    Payment payment =
            paymentRepository.findByOrderId(order.getId())
                    .orElse(null);


    if (payment == null) {

        payment = new Payment();

        payment.setOrder(order);
    }


    // =========================
    // UPDATE PAYMENT
    // =========================

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

    payment.setRazorpayPaymentId(null);

    payment.setRazorpaySignature(null);


    paymentRepository.save(payment);


    // =========================
    // RESPONSE
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


        if (order.getOrderStatus() == OrderStatus.CANCELLED) {

                throw new PaymentService_USER_Exception(
                        "This order has been cancelled."
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