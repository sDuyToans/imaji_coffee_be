package com.duytoan.imajicoffee.imaji_coffee_be.repository.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndCart_User_UserId(Long cartItemId, Long userId);
}
