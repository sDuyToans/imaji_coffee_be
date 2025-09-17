package com.duytoan.imajicoffee.imaji_coffee_be.services.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartDto;

public interface ICartService {
    CartDto getCart(Long userId);
    CartDto updateShipping(Long userId, Long shippingId);
    CartDto updatePromo(Long userId, Long promoId);
    void clearCart(Long userId);
}
