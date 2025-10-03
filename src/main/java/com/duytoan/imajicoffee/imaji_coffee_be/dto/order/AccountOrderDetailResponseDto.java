package com.duytoan.imajicoffee.imaji_coffee_be.dto.order;

import com.duytoan.imajicoffee.imaji_coffee_be.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class AccountOrderDetailResponseDto {
    Long orderId;
    Instant createdAt;
    OrderStatus status;
    Integer items;
    BigDecimal amount;
}
