package com.simanta.restaurant_backend.service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ForgotPassword_Otp_Service {

    private final JavaMailSender javaMailSender;

    public ForgotPassword_Otp_Service(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void OTPverifyLink(final String email, final String otp) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Your NH15 Restaurant login verification Code");

        String htmlContent = """
            <html>
            <body style="font-family: Arial, sans-serif;">

                <h2>NH15 Restaurant</h2>

                <p>Hello,</p>

                <p>
                    We received a login request for your NH15 Restaurant account.
                </p>

                <p>
                    Your One-Time Password (OTP) is:
                </p>

                <div style="
                    display:inline-block;
                    padding:15px 30px;
                    background-color:#1b291e;
                    color:white;
                    font-size:28px;
                    font-weight:bold;
                    border-radius:8px;
                    letter-spacing:5px;">
                    %s
                </div>

                <p style="margin-top:20px;">
                    This OTP is valid for <strong>5 minutes</strong>.
                </p>

                <p>
                    For your security, please do not share this code with anyone.
                </p>

                <p>
                    If you did not attempt to log in, you can safely ignore this email.
                </p>

                <br>

                <p>
                    Regards,<br>
                    NH15 Restaurant Team
                </p>

            </body>
            </html>
            """.formatted(otp);

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
