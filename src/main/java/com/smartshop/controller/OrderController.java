package com.smartshop.controller;

import com.smartshop.common.ApiResponse;
import com.smartshop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long customerId) {
        var order = orderService.placeOrder(customerId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(order)
                        .message("Order created successfully")
                        .build()
        );
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        var order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(order)
                        .message("Order retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/customer/{customerId}/order")
    public ResponseEntity<ApiResponse> getOrdersByCustomerId(@PathVariable Long customerId) {
        var orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(orders)
                        .message("Orders retrieved successfully")
                        .build()
        );
    }
}
