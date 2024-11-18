package com.smartshop.controller;

import com.smartshop.common.ApiResponse;
import com.smartshop.dto.request.AddCategoryRequest;
import com.smartshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        var categories = categoryService.getAllCategories();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(categories)
                        .message("Categories retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/category/{categoryId}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        var category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(category)
                        .message("Category retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        var category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(category)
                        .message("Category retrieved successfully")
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody AddCategoryRequest request) {
        var category = categoryService.addCategory(request.name());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(category)
                        .message("Category added successfully")
                        .build()
        );
    }

    @PutMapping("/category/{categoryId}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody AddCategoryRequest request) {
        var category = categoryService.updateCategory(categoryId, request.name());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(category)
                        .message("Category updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Category deleted successfully")
                        .build()
        );
    }
}