package com.smartshop.service.cart;

import com.smartshop.dto.response.CartResponse;
import com.smartshop.exception.NotFoundException;
import com.smartshop.mapper.CartMapper;
import com.smartshop.model.Cart;
import com.smartshop.repository.CartRepository;
import com.smartshop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CartMapper cartMapper;

    @Transactional
    @Override
    public CartResponse getCart(Long cartId) {
        updateCartTotalAmount(cartId);
        return cartRepository.findById(cartId)
                .map(cart -> cartMapper.toResponse(cart))
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));
    }

    @Transactional
    @Override
    public Long initializeNewCart(Long customerId) {
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        if (cartRepository.existsByCustomerId(customerId)) {
            throw new IllegalStateException("Customer already has a cart.");
        }

        Cart newCart = Cart.builder()
                .customer(customer)
                .totalAmount(BigDecimal.ZERO)
                .items(new HashSet<>())
                .build();
        return cartRepository.save(newCart).getId();
    }

    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException("Cart not found with id: " + cartId)
        );

        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));
        return cart.getTotalAmount();
    }

    @Transactional
    @Override
    public void updateCartTotalAmount(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));

        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId).orElseThrow(
                () -> new NotFoundException("Cart not found with customerId: " + customerId)
        );
    }
}
