package com.smartshop.service.cart;

import com.smartshop.exception.InsufficientStockException;
import com.smartshop.exception.NotFoundException;
import com.smartshop.model.Cart;
import com.smartshop.model.CartItem;
import com.smartshop.model.Product;
import com.smartshop.repository.CartItemRepository;
import com.smartshop.repository.CartRepository;
import com.smartshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity, Long customerId) {
        Cart cart = cartRepository.findById(cartId).orElseGet(() -> {
            Long newCartId = cartService.initializeNewCart(customerId);
            return cartRepository.findById(newCartId)
                    .orElseThrow(() -> new IllegalStateException("Failed to create and retrieve new cart."));
        });

        if (!cart.getCustomer().getId().equals(customerId)) {
            throw new IllegalStateException("You are not authorized to access this cart.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(0)
                            .unitPrice(product.getPrice())
                            .totalPrice(BigDecimal.ZERO)
                            .build();
                    cart.getItems().add(newItem);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);

        cartService.updateCartTotalAmount(cart.getId());
    }

    @Transactional
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("CartItem not found for product id: " + productId));

        cart.getItems().remove(cartItem);
        cartService.updateCartTotalAmount(cartId);
    }

    @Transactional
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Product not found in the cart with id: " + productId));

        Product product = cartItem.getProduct();

        int stockAvailable = product.getStock() + cartItem.getQuantity();
        if (quantity > stockAvailable) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);

        cartService.updateCartTotalAmount(cartId);
    }
}

