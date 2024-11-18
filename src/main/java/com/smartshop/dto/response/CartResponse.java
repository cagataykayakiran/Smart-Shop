package com.smartshop.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

@Builder
public record CartResponse(
        Long id,
        Set<CartItemResponse> items,
        BigDecimal totalAmount,
        Long customerId
) { }
