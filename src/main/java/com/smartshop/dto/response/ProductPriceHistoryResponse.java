package com.smartshop.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductPriceHistoryResponse(
    Long id,
    String productName,
    BigDecimal price,
    LocalDateTime effectiveDate,
    Long productId
) { }
