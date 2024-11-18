package com.smartshop.service.cart;

import com.smartshop.dto.response.CartResponse;
import com.smartshop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    CartResponse getCart(Long id);
    Long initializeNewCart(Long customerId);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    void updateCartTotalAmount(Long cartId);
    Cart getCartByCustomerId(Long customerId);
}
