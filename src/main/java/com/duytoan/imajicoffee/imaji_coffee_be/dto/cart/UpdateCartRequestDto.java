package com.duytoan.imajicoffee.imaji_coffee_be.dto.cart;

import lombok.Data;

@Data
public class UpdateCartRequestDto {
    private Long productId;
    private Integer quantity;
}
