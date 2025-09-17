package com.duytoan.imajicoffee.imaji_coffee_be.services.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemResponseDto;

public interface ICartItemService {
    CartItemResponseDto addItem(Long userId, CartItemRequestDto request);
    CartItemResponseDto updateItem(Long userId, Long cartItemId, int quantity);
    void removeItem(Long userId, Long cartItemId);
}
