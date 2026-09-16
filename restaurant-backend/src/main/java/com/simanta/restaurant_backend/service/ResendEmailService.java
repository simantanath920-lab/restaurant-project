package com.simanta.restaurant_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.simanta.restaurant_backend.exception.EmailSendingFailedException;

@Service
public class ResendEmailService {

    private final Resend resend;

    public ResendEmailService(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void sendHtmlEmail(String to, String subject, String html) {

        try {

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("NH15 Restaurant <onboarding@resend.dev>")
                    .to(to)
                    .subject(subject)
                    .html(html)
                    .build();

            resend.emails().send(params);

        } catch (ResendException e) {

            throw new EmailSendingFailedException("Failed to send email via Resend");
        }
    }
}