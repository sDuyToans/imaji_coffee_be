package com.duytoan.imajicoffee.imaji_coffee_be.controller;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderDetailResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.UpdateOrderStatusDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {
        // 1. create order
        OrderResponseDto orderResponseDto = orderService.createOrder(request);
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
