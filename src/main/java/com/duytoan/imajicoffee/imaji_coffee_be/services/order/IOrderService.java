package com.duytoan.imajicoffee.imaji_coffee_be.services.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.*;
import jakarta.mail.MessagingException;

import java.util.List;

/**
 * OrderItem interface contains method's name and parameters
 * @author duytoan
 * @since 10/2025
 */
public interface IOrderService {
    /**
     * Create new order
     * @param orderRequestDto -> order object
     * @param userId -> long
     * @return order request dto
     * @throws MessagingException -> throw mess exception
     */
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId) throws MessagingException;

    /**
     * Update order status
     * @param orderId -> long
     * @param orderStatusDto -> status dto
     * @return -> order object updated
     */
    OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto orderStatusDto);

    /**
     * Get order by id
     * @param orderId -> long
     * @return -> order detail dto
     */
    OrderDetailResponseDto getOrder(Long orderId);

    /**
     * Get list account's orders by userId
     * @param userId -> long
     * @return -> list account order detail dto
     */
    List<AccountOrderDetailResponseDto> getAccountOrders(Long userId);

    /**
     * Create order for PayPal method
     * @param orderRequestDto -> request dto
     * @param userId -> long
     * @return order dto
     * @throws MessagingException -> mess exception
     */
    OrderResponseDto createOrderForPaypal(OrderRequestDto orderRequestDto, Long userId) throws MessagingException;
}
