package com.duytoan.imajicoffee.imaji_coffee_be.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private String productCategory;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
}
