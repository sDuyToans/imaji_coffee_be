package com.duytoan.imajicoffee.imaji_coffee_be.repository.order;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Order item repository
 * @author duytoan
 * @since 10/2025
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
