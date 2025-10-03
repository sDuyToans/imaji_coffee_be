package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.email;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import com.duytoan.imajicoffee.imaji_coffee_be.services.email.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendOrderInfoToEmail(Order order) {
        String subject = "Imaji Coffee - Order #" + order.getOrderId();
        String body = buildBodyEmail(order);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(order.getEmail());
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    private String buildBodyEmail(Order order) {
        return "Hello - This is your order info";
    }
}
