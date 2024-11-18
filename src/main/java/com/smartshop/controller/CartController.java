package com.smartshop.controller;

import com.smartshop.common.ApiResponse;
import com.smartshop.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController{

    private final CartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        var cart = cartService.getCart(cartId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(cart)
                        .message("Cart retrieved successfully")
                        .build()
        );
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Cart cleared successfully")
                        .build()
        );
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId) {
        var totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(totalPrice)
                        .message("Total price retrieved successfully")
                        .build()
        );
    }
}
