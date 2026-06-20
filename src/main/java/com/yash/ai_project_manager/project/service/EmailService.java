package com.yash.ai_project_manager.project.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(
            JavaMailSender mailSender
    ) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(
            String toEmail,
            String token
    ) {

        String verificationLink =
                "http://localhost:8080/api/auth/verify-email?token="
                        + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Verify Your Email");
        message.setText(
                "Click the link below to verify your account:\n"
                        + verificationLink
        );

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(
            String toEmail,
            String token
    ) {

        String resetLink =
                "http://localhost:8080/api/auth/reset-password?token="
                        + token;

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Reset Password");
        message.setText(
                "Click the link below to reset your password:\n"
                        + resetLink
        );

        mailSender.send(message);
    }
}