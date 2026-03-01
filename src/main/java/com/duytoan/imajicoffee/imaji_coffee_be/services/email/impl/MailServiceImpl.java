package com.duytoan.imajicoffee.imaji_coffee_be.services.email.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import com.duytoan.imajicoffee.imaji_coffee_be.services.email.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

/**
 * Implemented MailService Interface -> Override and implement interface's methods
 * @author duytoan
 * @since 10/2025
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService {

    private final JavaMailSender javaMailSender;

    /**
     * Send order info to user's email
     * @param order -> order object
     * @throws MessagingException -> message exception
     */
    @Override
    public void sendOrderInfoToEmail(Order order) throws MessagingException {
        String subject = "Imaji Coffee - Order #" + order.getOrderId();
        String body = buildBodyEmail(order);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(order.getEmail());
        helper.setSubject(subject);
        helper.setFrom("imajicoffee@email.com");
        helper.setText(body, true); // true = HTML enabled

        javaMailSender.send(message);
    }

    /**
     * Build the body html for mail
     * @param order -> order object
     * @return string html code
     */
    private String buildBodyEmail(Order order) {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String orderItemsHtml = order.getOrderItems().stream()
                .map(item -> """
                <tr style="border-bottom:1px solid #eee;">
                    <td style="padding:10px;">
                        <img src='%s' width='60' style='border-radius:8px; vertical-align:middle;'>
                    </td>
                    <td style="padding:10px;">
                        <div style="font-weight:bold;">%s</div>
                        <div style="font-size:12px; color:#888;">%d × $%s</div>
                    </td>
                    <td style="padding:10px; text-align:right;">$%s</td>
                </tr>
                """.formatted(
                        item.getProductImg(),
                        item.getProductName(),
                        item.getQuantity(),
                        df.format(item.getPrice()),
                        df.format(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                )).collect(Collectors.joining());

        // safely handle nulls
        BigDecimal subtotal = order.getTotalAmount() != null ? order.getTotalAmount() : BigDecimal.ZERO;
        BigDecimal tax = order.getTaxAmount() != null ? order.getTaxAmount() : BigDecimal.ZERO;
        BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal shipping = order.getShippingAmount() != null ? order.getShippingAmount() : BigDecimal.ZERO;

        BigDecimal total = subtotal.add(tax).add(shipping).subtract(discount).setScale(2, RoundingMode.HALF_UP);

        return """
        <div style="font-family:Arial, sans-serif; color:#333; max-width:600px; margin:auto;">
          <h2 style="color:#4b2c20; text-align:center;">☕ Imaji Coffee</h2>
          <p>Hello,</p>
          <p>Thank you for your purchase! Here’s your order summary:</p>

          <table style="width:100%%; border-collapse:collapse; margin-top:20px;">
            %s
          </table>

          <hr style="margin:20px 0; border:none; border-top:1px solid #eee;">

          <table style="width:100%%; font-size:14px;">
            <tr><td>Subtotal:</td><td style="text-align:right;">$%s</td></tr>
            <tr><td>Tax (10%%):</td><td style="text-align:right;">$%s</td></tr>
            <tr><td>Discount:</td><td style="text-align:right;">-$%s</td></tr>
            <tr><td>Shipping:</td><td style="text-align:right;">$%s</td></tr>
            <tr><td style="padding-top:8px; font-weight:bold;">Grand Total:</td>
                <td style="text-align:right; font-weight:bold; font-size:16px;">$%s</td></tr>
          </table>

          <p style="margin-top:25px;">We’ll notify you once your order is shipped.</p>
          <p style="text-align:center; font-size:12px; color:#aaa;">&copy; 2025 Imaji Coffee</p>
        </div>
    """.formatted(
                orderItemsHtml,
                df.format(subtotal),
                df.format(tax),
                df.format(discount),
                df.format(shipping),
                df.format(total)
        );
    }
}
