package com.duytoan.imajicoffee.imaji_coffee_be.services.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.*;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.payment.PaymentIntentRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.OrderItem;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Ship;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.OrderStatus;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.OrderRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.ShipRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.UserRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final OrderItemServiceImpl orderItemService;
    private final PaymentServiceImpl paymentService;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional // if order fail -> roll back
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = mapToOrder(orderRequestDto);

        // create payment intent
        var paymentIntentResponse = paymentService.createPaymentIntent(
                new PaymentIntentRequestDto(
                        orderRequestDto.totalAmount().multiply(new BigDecimal("100")).longValue(),
                        orderRequestDto.currency()
                )
        );


        order.setPaymentIntentId(paymentIntentResponse.paymentIntentId());

        // save order
        Order savedOrder = orderRepository.save(order);

        // save order item
        orderItemService.saveOrderItems(savedOrder, orderRequestDto.items());


        return new OrderResponseDto(savedOrder.getOrderId(), savedOrder.getStatus().name(), paymentIntentResponse.clientSecret());
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto orderStatusDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId.toString()));
        order.setStatus(orderStatusDto.status());
        order.setUpdatedBy(orderStatusDto.updatedBy());
        order.setUpdatedAt(Instant.now());
        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(updatedOrder);
    }

    @Override
    public OrderDetailResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId.toString()));
        List<OrderItemResponseDto> orderItemResponseDtoList = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponseDto)
                .toList();
        return new OrderDetailResponseDto(
                order.getOrderId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getTaxAmount(),
                order.getShippingAmount(),
                order.getDiscountAmount(),
                order.getEmail(),
                order.getShippingAddress(),
                order.getShippingMethod(),
                order.getPaymentMethod(),
                order.getCreatedAt(),
                orderItemResponseDtoList
        );
    }

    private OrderItemResponseDto mapToOrderItemResponseDto(OrderItem orderItem) {
        OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto(
                orderItem.getProductId(),
                orderItem.getProductName(),
                orderItem.getProductImg(),
                orderItem.getProductCategory(),
                orderItem.getPrice(),
                orderItem.getQuantity()
        );
        return orderItemResponseDto;
    }

    private OrderResponseDto mapToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus().name(),
                null
        );
    }

    private Order mapToOrder(OrderRequestDto dto) {
        try {
            Order order = new Order();
            Ship ship = shipRepository.findById(dto.shipMethodId()).orElse(null);
            String shipMethod = ship != null ? ship.getMethodName() + " " + ship.getExpectedArrival() : "";

            User user = getOrCreateUser(dto.email());

            String shippingJSON = objectMapper.writeValueAsString(dto.shippingAddress());
            order.setUserId(user.getUserId());
            order.setEmail(dto.email());
            order.setShippingAddress(shippingJSON);
            order.setTotalAmount(dto.totalAmount());
            order.setTaxAmount(dto.taxAmount());
            order.setShippingAmount(dto.shippingAmount());
            order.setDiscountAmount(dto.discountAmount());
            order.setCurrency(dto.currency());
            order.setStatus(OrderStatus.PENDING);
            order.setShippingMethod(shipMethod);
            order.setPaymentMethod(dto.paymentMethod());
            order.setCreatedBy("ADMIN");
            order.setCreatedAt(Instant.now());
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map order OrderRequestDto to Order", e);
        }
    }

    private User getOrCreateUser(String email){
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User guest = new User();
                    guest.setEmail(email);
                    guest.setGuest(true);
                    guest.setCreatedBy("System");
                    guest.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    return userRepository.save(guest);
                });
    }
}
