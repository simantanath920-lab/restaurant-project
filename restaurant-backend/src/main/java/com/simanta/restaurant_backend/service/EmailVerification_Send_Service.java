package com.simanta.restaurant_backend.service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Async
public class EmailVerification_Send_Service {

    private final JavaMailSender javaMailSender;

    public EmailVerification_Send_Service(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
  
    @Transactional  
    public void emailVerificationLink(String email, String verificationToken)throws MessagingException {
 
        String verifyLink = "https://incomparable-beijinho-e46688.netlify.app/Verify_Email.html?token=" + verificationToken;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Verify Your Email - NH15 Restaurant");

        String htmlContent = """
            <html>
            <body style="font-family: Arial, sans-serif;">

                <h2>Welcome to NH15 Restaurant!</h2>

                <p>Thank you for registering with us.</p>

                <p>Please click the button below to verify your email address:</p>

                <a href="%s"
                style="
                        display:inline-block;
                        padding:12px 24px;
                        background-color:#1b291e;
                        color:white;
                        text-decoration:none;
                        border-radius:6px;
                        font-weight:bold;">
                    Verify Email
                </a>

                <p style="margin-top:20px;">
                    This verification link will expire in 30 minutes.
                </p>

                <p>
                    If you did not create this account, please ignore this email.
                </p>

                <br>

                <p>
                    Regards,<br>
                    NH15 Restaurant Team
                </p>

            </body>
            </html>
            """.formatted(verifyLink);

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
