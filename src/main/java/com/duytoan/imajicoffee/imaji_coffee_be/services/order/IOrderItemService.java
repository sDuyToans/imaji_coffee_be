package com.duytoan.imajicoffee.imaji_coffee_be.services.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderItemDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;

import java.util.List;

public interface IOrderItemService {
    public void saveOrderItems(Order order, List<OrderItemDto> orderItemDtoList);
}
