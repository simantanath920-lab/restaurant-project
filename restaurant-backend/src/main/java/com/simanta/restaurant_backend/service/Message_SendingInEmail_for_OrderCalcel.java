package com.simanta.restaurant_backend.service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class Message_SendingInEmail_for_OrderCalcel {

    private final JavaMailSender javaMailSender;

    public Message_SendingInEmail_for_OrderCalcel(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void message_Sending_for_OrderCancel(final String email,final Long orderid) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(email);
        helper.setSubject("NH15 Restaurant");

        String htmlContent = """
        
                <html>
                <body>

                    <h4>Your order has been cancelled</h4>
                    <p>Your Order #%s has been cancelled successfully</p>
                    <h2>Thank you </h2>

                    <p>
                        Regards,<br>
                        NH15 Restaurant Team
                    </p>

                </body>
                </html>

                """.formatted(orderid);

            helper.setText(htmlContent,true);

            javaMailSender.send(message);
    }
}
