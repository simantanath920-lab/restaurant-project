package com.simanta.restaurant_backend.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.model.Order;
import com.simanta.restaurant_backend.model.PaymentMethod;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class OwnerNotificationService {

    private final JavaMailSender javaMailSender;

    public OwnerNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void Sending_Message_for_OwnerNotification(final String email,final Order order,final PaymentMethod paymentMethod) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("NH15 Restaurant - New Order Received");

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

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}