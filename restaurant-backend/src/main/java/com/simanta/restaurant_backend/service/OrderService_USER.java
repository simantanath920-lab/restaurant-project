package com.simanta.restaurant_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.Address_View_response_USER_DTO;
import com.simanta.restaurant_backend.dto.OrderItem_Get_All_By_User_response_DTO;
import com.simanta.restaurant_backend.dto.Order_Cancel_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Order_Get_All_By_User_response_DTO;
import com.simanta.restaurant_backend.dto.Order_Get_By_Id_response_DTO;
import com.simanta.restaurant_backend.dto.Order_PostOrder_request_USER_DTO;
import com.simanta.restaurant_backend.dto.Order_PostOrder_response_USER_DTO;
import com.simanta.restaurant_backend.dto.Orderitem_Get_By_Id_response_DTO;
import com.simanta.restaurant_backend.exception.OrderService_USER_Exception;
import com.simanta.restaurant_backend.model.Address;
import com.simanta.restaurant_backend.model.Cart;
import com.simanta.restaurant_backend.model.CartItem;
import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.OrderItem;
import com.simanta.restaurant_backend.model.OrderStatus;
import com.simanta.restaurant_backend.model.Payment;
import com.simanta.restaurant_backend.model.PaymentMethod;
import com.simanta.restaurant_backend.model.PaymentStatus;
import com.simanta.restaurant_backend.model.User;
import com.simanta.restaurant_backend.repository.AddressRepository;
import com.simanta.restaurant_backend.repository.AuthRepository;
import com.simanta.restaurant_backend.repository.CartRepository;
import com.simanta.restaurant_backend.repository.OrderRepository;
import com.simanta.restaurant_backend.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService_USER {

    @Value("${restaurant.owner.email}")
    private String ownerEmail;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final AuthRepository authRepository;
    private final Message_SendingInEmail_for_Updates_Service message_SendingInEmail_for_Updates_Service;
    private final PaymentRepository paymentRepository;
    private final Message_SendingInEmail_for_OrderCalcel email_for_OrderCancel;
    private final OwnerNotificationService ownerNotificationService;

    public OrderService_USER(
            OrderRepository orderRepository,
            CartRepository cartRepository,
            AddressRepository addressRepository,
            AuthRepository authRepository,
            Message_SendingInEmail_for_Updates_Service message_SendingInEmail_for_Updates_Service,
            PaymentRepository paymentRepository,
            Message_SendingInEmail_for_OrderCalcel email_for_OrderCancel,
            OwnerNotificationService ownerNotificationService) {

        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.authRepository = authRepository;
        this.message_SendingInEmail_for_Updates_Service =
                message_SendingInEmail_for_Updates_Service;
        this.paymentRepository = paymentRepository;
        this.email_for_OrderCancel = email_for_OrderCancel;
        this.ownerNotificationService = ownerNotificationService;
    }

    // =========================
    // PLACE ORDER
    // =========================

    @Transactional
    public Order_PostOrder_response_USER_DTO placedOrder(
            final User user,
            final Order_PostOrder_request_USER_DTO order_PostOrder_request_USER_DTO) {

        final Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new OrderService_USER_Exception("Cart not found"));

        if (cart.getCartItem() == null || cart.getCartItem().isEmpty()) {

            throw new OrderService_USER_Exception("Cart is empty");
        }

        final Address address =
                addressRepository.findByIdAndUser(
                        order_PostOrder_request_USER_DTO.getAddressId(),
                        user
                )
                .orElseThrow(() ->
                        new OrderService_USER_Exception("Address not found"));

        Order order = new Order();

        order.setUser(user);
        order.setSelectdefaultaddress(address);

        List<OrderItem> orderItems = new ArrayList<>();

        double totalprice = 0;

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

            orderItems.add(orderItem);
        }

        double deliveryCharge =
                totalprice < 500 ? 40 : 0;

        double finaltotalprice =
                totalprice + deliveryCharge;

        order.setOrderitems(orderItems);
        order.setTotalprice(finaltotalprice);

        /*
         * Both COD and ONLINE orders initially remain PENDING.
         *
         * COD will continue immediately.
         *
         * ONLINE will become CONFIRMED only after
         * successful Razorpay verification.
         */

        order.setOrderStatus(OrderStatus.PENDING);

        orderRepository.save(order);


        // =========================
        // CASH ON DELIVERY
        // =========================

        if (order_PostOrder_request_USER_DTO.getPaymentMethod()
                == PaymentMethod.COD) {

            Payment payment = new Payment();

            payment.setOrder(order);
            payment.setPaymentMethod(PaymentMethod.COD);
            payment.setTotalAmount(order.getTotalprice());
            payment.setPaymentStatus(PaymentStatus.CREATED);

            paymentRepository.save(payment);


            // Customer notification

            message_SendingInEmail_for_Updates_Service
                    .Sending_Message_for_Notification(
                            order.getUser().getEmail(),
                            order.getId()
                    );


            // Owner notification

            ownerNotificationService
                    .Sending_Message_for_OwnerNotification(
                            ownerEmail,
                            order,
                            payment.getPaymentMethod()
                    );


            /*
             * COD is successfully placed immediately,
             * therefore clear the cart now.
             */

            cart.getCartItem().clear();

            cartRepository.save(cart);
        }


        /*
         * IMPORTANT:
         *
         * ONLINE payment does NOT clear the cart here.
         *
         * The cart will be cleared only after
         * Razorpay payment is successfully verified
         * inside PaymentService_USER.
         */

        return new Order_PostOrder_response_USER_DTO(
                "Order Placed.",
                order.getId()
        );
    }


    // =========================
    // GET ALL ORDER
    // =========================

    public Order_Get_All_By_User_response_DTO get_All_Order(
            final String email) {

        User user = authRepository.findByEmail(email)
                .orElseThrow(() ->
                        new OrderService_USER_Exception("User not found"));

        Order order = orderRepository.findByUser(user)
                .orElseThrow(() ->
                        new OrderService_USER_Exception("Order not found"));

        List<OrderItem_Get_All_By_User_response_DTO> orderitems =
                new ArrayList<>();

        double totalprice = 0;

        for (OrderItem orderItem : order.getOrderitems()) {

            OrderItem_Get_All_By_User_response_DTO orderitem =
                    new OrderItem_Get_All_By_User_response_DTO(
                            orderItem.getOrder().getId(),
                            orderItem.getMenu().getId(),
                            orderItem.getQuantity(),
                            orderItem.getPrice()
                    );

            double total =
                    orderItem.getQuantity()
                    * orderItem.getPrice();

            totalprice += total;

            orderitems.add(orderitem);
        }

        return new Order_Get_All_By_User_response_DTO(
                order.getId(),
                user.getId(),
                orderitems,
                order.getSelectdefaultaddress().getId(),
                order.getOrderStatus(),
                totalprice
        );
    }


    // =========================
    // GET ORDER BY ID
    // =========================

    public Order_Get_By_Id_response_DTO get_order_By_id(
            final Long orderid) {

        final Order order = orderRepository.findById(orderid)
                .orElseThrow(() ->
                        new OrderService_USER_Exception("Order not found"));

        List<Orderitem_Get_By_Id_response_DTO> orderitems =
                new ArrayList<>();

        double totalprice = 0;

        for (OrderItem orderItem : order.getOrderitems()) {

            Orderitem_Get_By_Id_response_DTO orderitem =
                    new Orderitem_Get_By_Id_response_DTO(
                            orderItem.getOrder().getId(),
                            orderItem.getMenu().getId(),
                            orderItem.getQuantity(),
                            orderItem.getPrice()
                    );

            double total =
                    orderItem.getQuantity()
                    * orderItem.getPrice();

            totalprice += total;

            orderitems.add(orderitem);
        }

        Address address =
                order.getSelectdefaultaddress();

        Address_View_response_USER_DTO address_View_response =
                new Address_View_response_USER_DTO(
                        address.getId(),
                        address.getFullName(),
                        address.getPhoneNumber(),
                        address.getStreet(),
                        address.getArea(),
                        address.getCity(),
                        address.getState(),
                        address.getPincode(),
                        address.getCountry(),
                        address.isDefault(),
                        address.getAddressType()
                );

        return new Order_Get_By_Id_response_DTO(
                order.getId(),
                order.getUser().getId(),
                orderitems,
                address_View_response,
                order.getOrderStatus(),
                totalprice
        );
    }


    // =========================
    // CANCEL ORDER
    // =========================

    @Transactional
    public Order_Cancel_response_USER_DTO cancel_order(
            final Long orderId,
            final Long userId) {

        final Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderService_USER_Exception("Order not found"));

        if (!order.getUser().getId().equals(userId)) {

            throw new OrderService_USER_Exception("User not found");
        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {

            return new Order_Cancel_response_USER_DTO(
                    "Order already canceled."
            );
        }

        if (order.getOrderStatus() == OrderStatus.CONFIRMED) {

            return new Order_Cancel_response_USER_DTO(
                    "Order is already confirmed, cannot cancel."
            );
        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {

            return new Order_Cancel_response_USER_DTO(
                    "Order is delivered cannot Cancel."
            );
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        email_for_OrderCancel
                .message_Sending_for_OrderCancel(
                        order.getUser().getEmail(),
                        orderId
                );

        return new Order_Cancel_response_USER_DTO(
                "Your order has been Canceled."
        );
    }
}