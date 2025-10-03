package com.duytoan.imajicoffee.imaji_coffee_be.controller.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.cart.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartServiceImpl cartService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.getCart(userId));
    }

    @PatchMapping("/shipping/{shipId}")
    public ResponseEntity<CartDto> updateShipping(Authentication authentication, @PathVariable Long shipId) {
        Long userId = getUserId(authentication);
        CartDto cartDto = cartService.updateShipping(userId, shipId);
        return ResponseEntity.ok().body(cartDto);
    }

    @PatchMapping("/promo/{promoId}")
    public ResponseEntity<CartDto> updatePromo(Authentication authentication, @PathVariable(required = false) Long promoId) {
        Long userId = getUserId(authentication);
        CartDto cartDto = cartService.updatePromo(userId, promoId);
        return ResponseEntity.ok().body(cartDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(Authentication authentication) {
        Long userId = getUserId(authentication);
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/promo")
    public ResponseEntity<CartDto> clearPromo(Authentication authentication) {
        Long userId = getUserId(authentication);
        CartDto cartDto = cartService.updatePromo(userId, null); // null means clear
        return ResponseEntity.ok().body(cartDto);
    }

    @PostMapping("/clearShip")
    public ResponseEntity<CartDto> clearShipping(Authentication authentication) {
        Long userId = getUserId(authentication);
        CartDto cartDto = cartService.clearShipping(userId);
        return ResponseEntity.ok().body(cartDto);
    }
}
