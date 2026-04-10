package com.food.foodorderingsystem.service;

import com.food.foodorderingsystem.entity.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    private final JavaMailSender mailSender;
    private final boolean isEmailEnabled;
    private final String fromAddress;

    public EmailNotificationService(
            JavaMailSender mailSender,
            @Value("${app.notifications.email.enabled:false}") boolean isEmailEnabled,
            @Value("${spring.mail.username}") String fromAddress
    ) {
        this.mailSender = mailSender;
        this.isEmailEnabled = isEmailEnabled;
        this.fromAddress = fromAddress;
    }

    public void sendOrderPlacedEmail(String to, Long orderId, BigDecimal totalAmount) {
        String subject = "Order placed successfully";
        String body = "Your order #" + orderId + " has been placed. Total amount: " + totalAmount;
        sendEmail(to, subject, body);
    }

    public void sendOrderStatusChangedEmail(String to, Long orderId, OrderStatus status) {
        String subject = "Order status updated";
        String body = "Your order #" + orderId + " status is now: " + status.name();
        sendEmail(to, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        if (!isEmailEnabled) {
            LOGGER.info("Email notifications disabled. Skipping email to {}", to);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            LOGGER.info("Order email sent successfully to {}", to);
        } catch (Exception exception) {
            LOGGER.error("Email send failed for {}", to, exception);
        }
    }
}
