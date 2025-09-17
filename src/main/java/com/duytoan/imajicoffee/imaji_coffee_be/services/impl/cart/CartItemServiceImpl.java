package com.duytoan.imajicoffee.imaji_coffee_be.services.impl.cart;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemRequestDto;
import com.duytoan.imajicoffee.imaji_coffee_be.dto.cart.CartItemResponseDto;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.CartItem;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.cart.Cart;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.product.Product;
import com.duytoan.imajicoffee.imaji_coffee_be.entities.user.User;
import com.duytoan.imajicoffee.imaji_coffee_be.exceptions.ResourceNotFoundException;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.auth.UserRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.cart.CartItemRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.cart.CartRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.repository.product.ProductRepository;
import com.duytoan.imajicoffee.imaji_coffee_be.services.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements ICartItemService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public CartItemResponseDto addItem(Long userId, CartItemRequestDto request) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    // if there is no cart for specific user, create one
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId.toString()));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", request.getProductId().toString()));

        // If product exists -> update quantity
        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem nCartItem = new CartItem();
                    nCartItem.setCart(cart);
                    nCartItem.setProduct(product);
                    return nCartItem; // no save here
                });

        if (item.getId() != null) {
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            item.setQuantity(request.getQuantity());
        }

        cartItemRepository.save(item);

        return mapToCartItemResponseDto(item);

    }

    @Override
    public CartItemResponseDto updateItem(Long userId, Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findByIdAndCart_User_UserId(cartItemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "CartItemId", cartItemId.toString()));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return mapToCartItemResponseDto(cartItem);
    }

    @Override
    public void removeItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findByIdAndCart_User_UserId(cartItemId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "CartItemId", cartItemId.toString()));

        cartItemRepository.delete(cartItem);
    }

    private CartItemResponseDto mapToCartItemResponseDto(CartItem cartItem) {
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
}
