package com.duytoan.imajicoffee.imaji_coffee_be.controller.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.UpdateQuantityCartItemRequest;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.cart.CartItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart/items")
public class CartItemController {

    private final CartItemServiceImpl cartItemService;

    private Long getUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDto> addItem(Authentication authentication, @RequestBody CartItemRequestDto cartItemRequestDto) {
        Long userId = getUserId(authentication);
        CartItemResponseDto cartItemResponseDto = cartItemService.addItem(userId, cartItemRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartItemResponseDto);
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponseDto> updateItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @RequestBody UpdateQuantityCartItemRequest request) {
        Long userId = getUserId(authentication);
        CartItemResponseDto updatedCartItemResponseDto = cartItemService.updateItem(userId, cartItemId, request.getQuantity());
        return ResponseEntity.status(HttpStatus.OK).body(updatedCartItemResponseDto);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeItem(Authentication authentication, @PathVariable Long cartItemId) {
        Long userId = getUserId(authentication);
        cartItemService.removeItem(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
