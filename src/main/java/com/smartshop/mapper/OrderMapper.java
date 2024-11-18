package com.smartshop.mapper;

import com.smartshop.dto.response.OrderResponse;
import com.smartshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    OrderResponse toResponse(Order order);
}