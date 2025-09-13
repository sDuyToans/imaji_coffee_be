package com.duytoan.imajicoffee.imaji_coffee_be.dto.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.address.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
        Long userId,

        @Email(message = "Email is invalid")
        @NotNull(message = "Email is required")
        String email,

        @NotNull(message = "Currency is required")
        String currency,

        @NotNull(message = "Total is required")
        BigDecimal totalAmount,

        @NotNull(message = "Tax amount is required")
        BigDecimal taxAmount,

        @NotNull(message = "Shipping Amount is required")
        BigDecimal shippingAmount,

        @NotNull(message = "Discount amount is required")
        BigDecimal discountAmount,

        @NotNull(message = "Payment method is required")
        String paymentMethod,

        @NotNull(message = "Shipping method id is required")
        Long shipMethodId,

        @NotNull(message = "Address is required")
        AddressDto shippingAddress,

        @NotNull(message = "Order items is required")
        List<OrderItemDto> items
) {
}
