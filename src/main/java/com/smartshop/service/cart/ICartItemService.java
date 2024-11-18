package com.smartshop.service.cart;


public interface ICartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity, Long customerId);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
}
