package com.duytoan.imajicoffee.imaji_coffee_be.repository;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
