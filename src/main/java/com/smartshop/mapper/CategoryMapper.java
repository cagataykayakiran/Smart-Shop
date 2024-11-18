package com.smartshop.mapper;

import com.smartshop.dto.response.CategoryResponse;
import com.smartshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryResponse toResponse(Category category);
}
