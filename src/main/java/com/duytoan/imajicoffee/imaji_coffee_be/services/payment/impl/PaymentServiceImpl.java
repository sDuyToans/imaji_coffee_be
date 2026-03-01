package com.duytoan.imajicoffee.imaji_coffee_be.services.payment.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.payment.PaymentIntentRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.payment.PaymentIntentResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.payment.IPaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

/**
 * Implemented PaymentService Interface -> Override and implement interface's methods
 * @author duytoan
 * @since 10/2025
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

    /**
     * Create payment intent info for stripe
     * @param requestDto -> intent dto
     * @return Payment intent response dto
     */
    @Override
    public PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto requestDto) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(requestDto.amount())
                    .setCurrency(requestDto.currency())
                    .addPaymentMethodType("card")
                    .putMetadata("orderId", String.valueOf(requestDto.orderId()))
                    .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return new PaymentIntentResponseDto(paymentIntent.getId(), paymentIntent.getClientSecret());
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create payment intent", e);
        }
    }
}
