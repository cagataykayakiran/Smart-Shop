package com.smartshop.service.order;

import com.smartshop.dto.response.OrderResponse;
import com.smartshop.exception.EmptyCartException;
import com.smartshop.exception.InsufficientStockException;
import com.smartshop.exception.NotFoundException;
import com.smartshop.mapper.OrderMapper;
import com.smartshop.model.*;
import com.smartshop.repository.OrderRepository;
import com.smartshop.repository.ProductPriceHistoryRepository;
import com.smartshop.repository.ProductRepository;
import com.smartshop.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ProductPriceHistoryRepository productPriceHistoryRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse placeOrder(Long customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cart is empty. Cannot place an order.");
        }

        Order order = createOrder(cart);
        Set<OrderItem> orderItems = createOrderItems(order, cart);

        order.setOrderItems(orderItems);
        order.setTotalAmount(calculateTotalAmount(orderItems));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return orderMapper.toResponse(savedOrder);
    }

    private Order createOrder(Cart cart) {
        return Order.builder()
                .customer(cart.getCustomer())
                .orderDate(LocalDate.from(LocalDateTime.now()))
                .build();
    }

    private Set<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();

                    if (product.getStock() < cartItem.getQuantity()) {
                        throw new InsufficientStockException(
                                "Insufficient stock for product: " + product.getName()
                        );
                    }

                    saveProductPriceHistory(product);

                    product.setStock(product.getStock() - cartItem.getQuantity());
                    productRepository.save(product);

                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getUnitPrice())
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private void saveProductPriceHistory(Product product) {
        ProductPriceHistory history = ProductPriceHistory.builder()
                .product(product)
                .price(product.getPrice())
                .effectiveDate(LocalDateTime.now())
                .build();

        productPriceHistoryRepository.save(history);
    }

    private BigDecimal calculateTotalAmount(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }
}

