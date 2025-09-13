package com.duytoan.imajicoffee.imaji_coffee_be.services;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.payment.PaymentIntentRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.payment.PaymentIntentResponseDto;

public interface IPaymentService {
    PaymentIntentResponseDto createPaymentIntent(PaymentIntentRequestDto paymentIntentRequestDto);
}
