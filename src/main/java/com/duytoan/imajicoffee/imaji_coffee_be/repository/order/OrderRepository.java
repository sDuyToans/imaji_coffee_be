package com.duytoan.imajicoffee.imaji_coffee_be.repository.order;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByPaymentIntentId(String paymentIntentId);
    List<Order> findByUserId(Long userId);
}
