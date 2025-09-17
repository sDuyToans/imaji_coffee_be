package com.duytoan.imajicoffee.imaji_coffee_be.dto.cart;

import lombok.Data;

@Data
public class AddOrUpdatePromoRequestDto {
    private Long cartId;
    private Long promoId;
}
