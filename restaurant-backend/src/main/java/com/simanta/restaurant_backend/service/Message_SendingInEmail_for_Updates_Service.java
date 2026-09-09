package com.simanta.restaurant_backend.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class Message_SendingInEmail_for_Updates_Service {

    private final JavaMailSender javaMailSender;

    public Message_SendingInEmail_for_Updates_Service(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void Sending_Message_for_Notification(final String email,final Long orderId) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(email);
        helper.setSubject("NH15 Restaurant");

        String htmlContent = """
        
                <html>
                <body>

                    <h4>Order placed successfully</h4>
                    <p>Your Order #%s has been placed successfully</p>
                    <h2>Thank you </h2>

                    <p>
                        Regards,<br>
                        NH15 Restaurant Team
                    </p>

                </body>
                </html>

                """.formatted(orderId);

            helper.setText(htmlContent,true);

            javaMailSender.send(message);
        }

}
