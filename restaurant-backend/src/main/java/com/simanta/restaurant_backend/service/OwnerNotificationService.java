package com.simanta.restaurant_backend.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.PaymentMethod;

@Service
public class OwnerNotificationService {

    private final ResendEmailService resendEmailService;

    public OwnerNotificationService(ResendEmailService resendEmailService) {
        this.resendEmailService = resendEmailService;
    }

    @Async
    public void Sending_Message_for_OwnerNotification(
            final String email,
            final Order order,
            final PaymentMethod paymentMethod) {

        String payment = paymentMethod == PaymentMethod.COD
                ? "Cash on Delivery"
                : "Online Payment";

        String htmlContent = """
                <html>
                <body>

                    <h2>🔔 New Order Received</h2>

                    <p>
                        New order <strong>#%s</strong> has been received.
                    </p>

                    <p>
                        <strong>Customer:</strong> %s<br>
                        <strong>Total:</strong> ₹%.2f<br>
                        <strong>Payment:</strong> %s
                    </p>

                    <p>
                        <strong>Tap to view order details.</strong>
                    </p>

                    <p>
                        Regards,<br>
                        NH15 Restaurant
                    </p>

                </body>
                </html>
                """.formatted(
                        order.getId(),
                        order.getUser().getName(),
                        order.getTotalprice(),
                        payment
                );

        resendEmailService.sendHtmlEmail(
                email,
                "NH15 Restaurant - New Order Received",
                htmlContent
        );
    }
}