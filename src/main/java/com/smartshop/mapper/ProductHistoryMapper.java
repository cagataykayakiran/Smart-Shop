package com.smartshop.mapper;

import com.smartshop.dto.response.ProductPriceHistoryResponse;
import com.smartshop.model.ProductPriceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductHistoryMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productId", source = "product.id")
    ProductPriceHistoryResponse toResponse(ProductPriceHistory history);
}
