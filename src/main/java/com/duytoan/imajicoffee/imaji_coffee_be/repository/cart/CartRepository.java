package com.duytoan.imajicoffee.imaji_coffee_be.repository.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser_UserId(Long userId);
    void deleteByUser_UserId(Long userId);
}
