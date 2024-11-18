package com.smartshop.service.order;

import com.smartshop.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse placeOrder(Long customerId);
    List<OrderResponse> getOrdersByCustomerId(Long customerId);
    OrderResponse getOrderById(Long id);
}
