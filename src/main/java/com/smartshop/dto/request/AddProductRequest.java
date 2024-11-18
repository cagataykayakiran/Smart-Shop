package com.smartshop.dto.request;

import java.math.BigDecimal;

public record AddProductRequest(
        String name,
        BigDecimal price,
        int stock,
        String categoryName
) {}
