package com.duytoan.imajicoffee.imaji_coffee_be.dto.cart;

import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long productId;
    private Integer quantity;
}
