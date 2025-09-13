package com.duytoan.imajicoffee.imaji_coffee_be.services.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.order.OrderItemDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.OrderItem;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Product;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.ProductImage;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.OrderItemRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.ProductRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Override
    public void saveOrderItems(Order order, List<OrderItemDto> orderItemDtoList) {
        List<OrderItem> orderItems = orderItemDtoList.stream().map(itemDto -> {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", itemDto.productId().toString()));
            // Deduct product quantity
            int newQuantity = product.getQuantity() - itemDto.quantity();
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
            product.setQuantity(newQuantity);
            productRepository.save(product);

            //create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getProductId());
            orderItem.setProductName(product.getName());
            String productImg = product.getProductImages()
                    .stream()
                    .filter(ProductImage::getIsMain)
                    .findFirst()
                    .map(ProductImage::getImageUrl)
                    .orElse(null);
            orderItem.setProductImg(productImg);
            orderItem.setProductCategory(product.getCategory());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setCreatedBy("ADMIN");
            return orderItem;
        }).toList();

        orderItemRepository.saveAll(orderItems);
    }
}
