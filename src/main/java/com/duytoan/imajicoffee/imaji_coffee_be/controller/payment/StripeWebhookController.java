package com.duytoan.imajicoffee.imaji_coffee_be.controller.payment;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.UpdateOrderStatusDto;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.OrderStatus;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.order.OrderServiceImpl;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final OrderServiceImpl orderService;
    @Value("${stripe.webhook.secretKey}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader){
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
                String orderId = paymentIntent.getMetadata().get("orderId");
                if (orderId != null && !orderId.isEmpty()) {
                    orderService.updateOrderStatus(Long.parseLong(orderId), new UpdateOrderStatusDto(OrderStatus.PAID, "SYSTEM"));
                } else {
                    System.out.println("Missing orderId in metadata");
                }
             }
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
