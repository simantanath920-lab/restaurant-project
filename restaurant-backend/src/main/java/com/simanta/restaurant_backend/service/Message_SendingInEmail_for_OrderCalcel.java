package com.simanta.restaurant_backend.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Message_SendingInEmail_for_OrderCalcel {

    private final ResendEmailService resendEmailService;

    public Message_SendingInEmail_for_OrderCalcel(
            ResendEmailService resendEmailService) {
        this.resendEmailService = resendEmailService;
    }

    @Async
    public void message_Sending_for_OrderCancel(
            final String email,
            final Long orderid) {

        String htmlContent = """
                <html>
                <body>

                    <h4>Your order has been cancelled</h4>

                    <p>
                        Your Order #%s has been cancelled successfully
                    </p>

                    <h2>Thank you</h2>

                    <p>
                        Regards,<br>
                        NH15 Restaurant Team
                    </p>

                </body>
                </html>
                """.formatted(orderid);

        resendEmailService.sendHtmlEmail(
                email,
                "NH15 Restaurant",
                htmlContent
        );
    }
}