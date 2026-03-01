package com.duytoan.imajicoffee.imaji_coffee_be.services.cart.impl;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.common.PromoDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.product.ShipMethodDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.CartItem;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.cart.Cart;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Promo;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Ship;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.cart.CartRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.product.PromoRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.product.ShipRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implemented CartService Interface -> Override and implement interface's methods
 * @author duytoan
 * @since 10/2025
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final ShipRepository shipRepository;
    private final PromoRepository promoRepository;

    /**
     * Get current user cart by userId
     * @param userId -> long userId
     * @return CartDto Object
     */
    @Override
    public CartDto getCart(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Cart", "UserId", userId.toString())
        );
        return mapToCartDto(cart);
    }

    /**
     * Update ShippingMethodDto in CartDto Object
     * @param userId -> long userId
     * @param shippingId -> long shippingId
     * @return CartDto Updated
     */
    @Override
    public CartDto updateShipping(Long userId, Long shippingId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));

        Ship ship = shipRepository.findById(shippingId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping", "ShippingId", shippingId.toString()));

        cart.setShipMethod(ship);
        cartRepository.save(cart);
       return mapToCartDto(cart);
    }

    /**
     * Update PromoDto Object in CartDto Object
     * @param userId -> long userId
     * @param promoId -> long promoId
     * @return Updated CartDto Object
     */
    @Override
    public CartDto updatePromo(Long userId, Long promoId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "UserId", userId.toString()));

        if (promoId == null) {
            // Clear promo if null
            cart.setPromo(null);
        } else {
            Promo promo = promoRepository.findById(promoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Promo", "Id", promoId.toString()));
            cart.setPromo(promo);
        }

        cartRepository.save(cart);
        return mapToCartDto(cart);
    }

    /**
     * Remove ShippingMethodDto from CartDto Object
     * @param userId -> long userId
     * @return Updated CartDto Object
     */
    @Override
    public CartDto clearShipping(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "userId", userId.toString()));
        Ship freeShip = shipRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Ship", "shipMethodId", String.valueOf(1)));
        cart.setShipMethod(freeShip);
        cartRepository.save(cart);
        return mapToCartDto(cart);
    }

    /**
     * Clear Current CartDto Object
     * @param userId -> long userId
     */
    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId.toString()));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    /**
     * Map Cart Object To CartDto Object
     * @param cart -> cart object
     * @return CartDto Object
     */
    private CartDto mapToCartDto(Cart cart) {
        BigDecimal subtotal = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.1));
        BigDecimal shipping = cart.getShipMethod() != null ? cart.getShipMethod().getPrice() : BigDecimal.ZERO;

        BigDecimal discount = BigDecimal.ZERO;
        if (cart.getPromo() != null) {
            switch (cart.getPromo().getDiscountType()) {
                case "percentage" -> discount = subtotal.multiply(cart.getPromo().getDiscountValue()
                        .divide(BigDecimal.valueOf(100), RoundingMode.CEILING));
                case "fixed" -> discount = cart.getPromo().getDiscountValue();
                case "free_shipping" -> shipping = BigDecimal.ZERO;
            }
        }

        BigDecimal total = subtotal.add(tax).add(shipping).subtract(discount);

        return new CartDto(
                cart.getId(),
                cart.getUser().getUserId(),
                cart.getCartItems().stream().map(this::mapToCartItemDto).toList(),
                cart.getShipMethod() != null ? mapToShipMethodDto(cart.getShipMethod()) : null,
                cart.getPromo() != null ? mapToPromoDto(cart.getPromo()) : null,
                subtotal, tax, shipping, discount, total
        );
    }

    /**
     * Map CartItem To CartItemDto Object
     * @param cartItem -> cartItem object
     * @return CartItemDto Object
     */
    private CartItemResponseDto mapToCartItemDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getProduct().getProductId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getCategory(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity(),
                cartItem.getProduct().getMainImageUrl()
        );
    }

    /**
     * Map Ship To ShipMethodDto Object
     * @param ship -> ship object
     * @return ShipMethodDto Object
     */
    private ShipMethodDto mapToShipMethodDto(Ship ship) {
        ShipMethodDto shipMethodDto = new ShipMethodDto();
        BeanUtils.copyProperties(ship, shipMethodDto);
        return shipMethodDto;
    }

    /**
     * Map Promo To PromoDto Object
     * @param promo -> promo object
     * @return PromoDto Object
     */
    private PromoDto mapToPromoDto(Promo promo) {
        PromoDto promoDto = new PromoDto();
        BeanUtils.copyProperties(promo, promoDto);
        return promoDto;
    }
}
