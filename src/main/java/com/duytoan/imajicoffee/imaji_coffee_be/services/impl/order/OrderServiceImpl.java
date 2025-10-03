package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.order;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.*;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.payment.PaymentIntentRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.OrderItem;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Ship;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import com.duytoan.imajicoffee.imaji_coffee_be.enums.OrderStatus;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.auth.UserRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.order.OrderRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.product.ShipRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.email.IMailService;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.address.AddressServiceImpl;
import com.duytoan.imajicoffee.imaji_coffee_be.services.impl.payment.PaymentServiceImpl;
import com.duytoan.imajicoffee.imaji_coffee_be.services.order.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    private final AddressServiceImpl addressServiceImpl;
    private final IMailService mailService;

    @Override
    @Transactional // if order fail -> roll back
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId) {
        Order order = mapToOrder(orderRequestDto, userId);


        // save order
        Order savedOrder = orderRepository.save(order);

        // create payment intent
        var paymentIntentResponse = paymentService.createPaymentIntent(
                new PaymentIntentRequestDto(
                        savedOrder.getOrderId(),
                        orderRequestDto.totalAmount().multiply(new BigDecimal("100")).longValue(),
                        orderRequestDto.currency()
                )
        );


        order.setPaymentIntentId(paymentIntentResponse.paymentIntentId());

        // save order item
        orderItemService.saveOrderItems(savedOrder, orderRequestDto.items());

        // save address
        if (userId != null){
            addressServiceImpl.saveAddressForOder(orderRequestDto.shippingAddress(), userId);
        }

        // send email
//        mailService.sendOrderInfoToEmail(order);

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

    @Override
    public List<AccountOrderDetailResponseDto> getAccountOrders(Long userId) {
        List<AccountOrderDetailResponseDto> orderList = orderRepository.findByUserId(userId)
                .stream().map(this::mapToAccountOderDetail)
                .toList();
        return orderList;
    }

    private AccountOrderDetailResponseDto mapToAccountOderDetail(Order order) {
        AccountOrderDetailResponseDto accountOrderDetailResponseDto = new AccountOrderDetailResponseDto();
        BeanUtils.copyProperties(order, accountOrderDetailResponseDto);
        accountOrderDetailResponseDto.setItems(order.getOrderItems().size());
        accountOrderDetailResponseDto.setAmount(order.getTotalAmount());
        return accountOrderDetailResponseDto;
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

    private Order mapToOrder(OrderRequestDto dto, Long userId) {
        try {
            Order order = new Order();
            Ship ship = shipRepository.findById(dto.shipMethodId()).orElse(null);
            String shipMethod = ship != null ? ship.getMethodName() + " " + ship.getExpectedArrival() : "";

            User user;
            if (userId != null) {
                // Logged-in user
                user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId.toString()));
            } else {
                // Guest checkout
                user = getOrCreateUser(dto.email());
            }

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
            throw new RuntimeException("Failed to map OrderRequestDto to Order", e);
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
