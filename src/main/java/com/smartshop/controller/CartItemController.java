package com.smartshop.controller;

import com.smartshop.common.ApiResponse;
import com.smartshop.service.cart.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam Long customerId
    ) {
        cartItemService.addItemToCart(cartId, productId, quantity, customerId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Item added to cart successfully")
                        .build()
        );
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long itemId
    ) {
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Item removed from cart successfully")
                        .build()
        );
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateCartItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam int quantity
    ) {
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Item quantity updated successfully")
                        .build()
        );
    }
}
