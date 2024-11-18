package com.smartshop.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemResponse(
        Long id,
        Integer quantity,
        BigDecimal unitPrice,
        Long productId,
        String productName
) { }
