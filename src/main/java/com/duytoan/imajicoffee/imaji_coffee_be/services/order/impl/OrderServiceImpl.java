package com.duytoan.imajicoffee.imaji_coffee_be.services.order.impl;

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
import com.duytoan.imajicoffee.imaji_coffee_be.services.address.impl.AddressServiceImpl;
import com.duytoan.imajicoffee.imaji_coffee_be.services.email.IMailService;
import com.duytoan.imajicoffee.imaji_coffee_be.services.order.IOrderService;
import com.duytoan.imajicoffee.imaji_coffee_be.services.payment.impl.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implemented OrderItem Interface -> Override and implement interface's methods
 * @author duytoan
 * @since 10/2025
 */
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

    /**
     * Create new order
     * @param orderRequestDto -> order object
     * @param userId -> long
     * @return order request dto
     * @throws MessagingException -> throw mess exception
     */
    @Override
    @Transactional // if order fail -> roll back
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Long userId) throws MessagingException {
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

//         send email
        mailService.sendOrderInfoToEmail(order);

        return new OrderResponseDto(savedOrder.getOrderId(), savedOrder.getStatus().name(), paymentIntentResponse.clientSecret());
    }

    /**
     * Update order status
     * @param orderId -> long
     * @param orderStatusDto -> status dto
     * @return -> order object updated
     */
    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderStatusDto orderStatusDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId.toString()));
        order.setStatus(orderStatusDto.status());
        order.setUpdatedBy(orderStatusDto.updatedBy());
        order.setUpdatedAt(Instant.now());
        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(updatedOrder);
    }

    /**
     * Get order by id
     * @param orderId -> long
     * @return -> order detail dto
     */
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

    /**
     * Get list account's orders by userId
     * @param userId -> long
     * @return -> list account order detail dto
     */
    @Override
    public List<AccountOrderDetailResponseDto> getAccountOrders(Long userId) {
        List<AccountOrderDetailResponseDto> orderList = orderRepository.findByUserId(userId)
                .stream().map(this::mapToAccountOderDetail)
                .toList();
        return orderList;
    }

    /**
     * Create order for PayPal method
     * @param orderRequestDto -> request dto
     * @param userId -> long
     * @return order dto
     * @throws MessagingException -> mess exception
     */
    @Override
    public OrderResponseDto createOrderForPaypal(OrderRequestDto orderRequestDto, Long userId) throws MessagingException {
        Order order = mapToOrder(orderRequestDto, userId);
        // save order
        Order savedOrder = orderRepository.save(order);
        // save order item
        orderItemService.saveOrderItems(savedOrder, orderRequestDto.items());
        // save address
        if (userId != null){
            addressServiceImpl.saveAddressForOder(orderRequestDto.shippingAddress(), userId);
        }
        // send email
        mailService.sendOrderInfoToEmail(order);
        return new OrderResponseDto(savedOrder.getOrderId(), savedOrder.getStatus().name(), null);
    }

    /**
     * Map order to account order detail dto
     * @param order -> order object
     * @return -> account order detail dto
     */
    private AccountOrderDetailResponseDto mapToAccountOderDetail(Order order) {
        AccountOrderDetailResponseDto accountOrderDetailResponseDto = new AccountOrderDetailResponseDto();
        BeanUtils.copyProperties(order, accountOrderDetailResponseDto);
        accountOrderDetailResponseDto.setItems(order.getOrderItems().size());
        accountOrderDetailResponseDto.setAmount(order.getTotalAmount());
        return accountOrderDetailResponseDto;
    }

    /**
     * Map order item to dto
     * @param orderItem -> object
     * @return -> order item res dto
     */
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

    /**
     * Map order to order res dto
     * @param order -> object
     * @return -> order rest dto
     */
    private OrderResponseDto mapToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus().name(),
                null
        );
    }

    /**
     * Map order request dto to order
     * @param dto -> order req dto
     * @param userId -> long
     * @return -> order object
     */
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

    /**
     * Get user if existed otherwise create user
     * @param email -> string
     * @return -> user object
     */
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
