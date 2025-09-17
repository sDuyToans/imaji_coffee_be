package com.duytoan.imajicoffee.imaji_coffee_be.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDto {
    private Long productId;
    private Integer quantity;
}
