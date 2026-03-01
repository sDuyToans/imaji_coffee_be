package com.duytoan.imajicoffee.imaji_coffee_be.repository.order;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Order repository
 * @author duytoan
 * @since 10/2025
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find order by payment intent id
     * @param paymentIntentId
     * @return order
     */
    Order findByPaymentIntentId(String paymentIntentId);

    /**
     * Find orders by user id
     * @param userId
     * @return order list
     */
    List<Order> findByUserId(Long userId);
}
