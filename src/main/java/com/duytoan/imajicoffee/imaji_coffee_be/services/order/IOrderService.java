package com.duytoan.imajicoffee.imaji_coffee_be.services.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderDetailResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.UpdateOrderStatusDto;

public interface IOrderService {
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId);
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto orderStatusDto);
    public OrderDetailResponseDto getOrder(Long orderId);
}
