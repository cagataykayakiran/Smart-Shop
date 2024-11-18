package com.smartshop.service.category;

import com.smartshop.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {

    CategoryResponse getCategoryById(Long id);
    CategoryResponse getCategoryByName(String categoryName);
    List<CategoryResponse> getAllCategories();
    CategoryResponse addCategory(String categoryName);
    CategoryResponse updateCategory(Long id, String name);
    void deleteCategoryById(Long id);

}
