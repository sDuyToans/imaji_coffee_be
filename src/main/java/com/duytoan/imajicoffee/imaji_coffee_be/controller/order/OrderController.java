package com.duytoan.imajicoffee.imaji_coffee_be.controller.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderDetailResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.UpdateOrderStatusDto;
import com.duytoan.imajicoffee.imaji_coffee_be.services.order.IOrderService;
import com.duytoan.imajicoffee.imaji_coffee_be.utils.GetAuthenticationInfo;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author duytoan
 * @since 10/2025
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final IOrderService orderService;
    private final GetAuthenticationInfo getAuthenticationInfo;

    /**
     * Create order
     * @param request -> order request dto
     * @return -> res entity
     * @throws MessagingException -> mess ex
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto request
    ) throws MessagingException {
        Long userId = getAuthenticationInfo.getUserId();
        OrderResponseDto orderResponseDto = orderService.createOrder(request, userId);
        return ResponseEntity.ok(orderResponseDto);
    }

    /**
     * Create order for PayPal
     * @param request -> order request dto
     * @return -> res entity
     * @throws MessagingException -> mess ex
     */
    @PostMapping("/paypal")
    public ResponseEntity<OrderResponseDto> createOrderForPayPal(@Valid @RequestBody OrderRequestDto request) throws MessagingException {
        Long userId = getAuthenticationInfo.getUserId();
        OrderResponseDto orderResponseDto = orderService.createOrderForPaypal(request, userId);
        return ResponseEntity.ok(orderResponseDto);
    }

    /**
     * Get order by order id
     * @param orderId -> long order id
     * @return -> res entity {order}
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    /**
     * Update order status
     * @param orderId -> long order id
     * @param updateOrderStatusDto -> update order status dto
     * @return -> res entity
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, updateOrderStatusDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderResponseDto);
    }
}
