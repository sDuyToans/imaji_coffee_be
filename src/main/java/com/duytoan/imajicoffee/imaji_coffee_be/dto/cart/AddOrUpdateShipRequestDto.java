package com.duytoan.imajicoffee.imaji_coffee_be.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOrUpdateShipRequestDto {
    private Long cartId;
    private Long shipId;
}
