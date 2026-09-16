package com.simanta.restaurant_backend.service;

import org.springframework.stereotype.Service;

@Service
public class ForgotPassword_Otp_Service {

    private final ResendEmailService resendEmailService;

    public ForgotPassword_Otp_Service(ResendEmailService resendEmailService) {
        this.resendEmailService = resendEmailService;
    }

    public void OTPverifyLink(final String email, final String otp) {

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

        resendEmailService.sendHtmlEmail(
                email,
                "Your NH15 Restaurant login verification Code",
                htmlContent
        );
    }
}