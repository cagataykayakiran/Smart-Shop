package com.smartshop.mapper;

import com.smartshop.dto.response.CartResponse;
import com.smartshop.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "items", source = "items")
    CartResponse toResponse(Cart cart);
}