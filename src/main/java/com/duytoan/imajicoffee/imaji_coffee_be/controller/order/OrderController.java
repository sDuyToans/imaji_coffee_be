package com.duytoan.imajicoffee.imaji_coffee_be.controller.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderDetailResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.UpdateOrderStatusDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.order.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            Authentication authentication,
            @Valid @RequestBody OrderRequestDto request
    ) {
        Long userId = authentication != null ? Long.parseLong(authentication.getName()) : null;
        OrderResponseDto orderResponseDto = orderService.createOrder(request, userId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, updateOrderStatusDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDto);
    }
}
