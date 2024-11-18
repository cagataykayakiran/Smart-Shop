package com.smartshop.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse (

    Long id,
    BigDecimal totalAmount,
    List<OrderItemResponse> items,
    LocalDateTime orderDate,
    Long customerId
) { }