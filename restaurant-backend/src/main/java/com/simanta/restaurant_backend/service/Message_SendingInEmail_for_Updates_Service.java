package com.simanta.restaurant_backend.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Message_SendingInEmail_for_Updates_Service {

    private final ResendEmailService resendEmailService;

    public Message_SendingInEmail_for_Updates_Service(
            ResendEmailService resendEmailService) {
        this.resendEmailService = resendEmailService;
    }

    @Async
    public void Sending_Message_for_Notification(
            final String email,
            final Long orderId) {

        String htmlContent = """
                <html>
                <body>

                    <h4>Order placed successfully</h4>

                    <p>
                        Your Order #%s has been placed successfully
                    </p>

                    <h2>Thank you</h2>

                    <p>
                        Regards,<br>
                        NH15 Restaurant Team
                    </p>

                </body>
                </html>
                """.formatted(orderId);

        resendEmailService.sendHtmlEmail(
                email,
                "NH15 Restaurant",
                htmlContent
        );
    }
}