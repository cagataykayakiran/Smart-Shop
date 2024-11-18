package com.smartshop.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponse(

    Long id,
    Long productId,
    String productName,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal totalPrice
) { }
