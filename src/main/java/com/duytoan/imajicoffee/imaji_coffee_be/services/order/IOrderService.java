package com.duytoan.imajicoffee.imaji_coffee_be.services.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.*;

import java.util.List;

public interface IOrderService {
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId);
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto orderStatusDto);
    public OrderDetailResponseDto getOrder(Long orderId);
    public List<AccountOrderDetailResponseDto> getAccountOrders(Long userId);
}
