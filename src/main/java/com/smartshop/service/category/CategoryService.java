package com.smartshop.service.category;

import com.smartshop.dto.response.CategoryResponse;
import com.smartshop.exception.AlreadyExistsException;
import com.smartshop.exception.NotFoundException;
import com.smartshop.mapper.CategoryMapper;
import com.smartshop.model.Category;
import com.smartshop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> categoryMapper.toResponse(category))
                .orElseThrow(() -> new NotFoundException("Product not found " + categoryId));
    }

    @Override
    public CategoryResponse getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .map(category -> categoryMapper.toResponse(category))
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }


    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(
                category -> categoryMapper.toResponse(category)).toList();
    }

    @Override
    public CategoryResponse addCategory(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new AlreadyExistsException("Category already exists");
        }

        var newCategory = Category.builder()
                .name(categoryName)
                .build();

        Category savedCategory = categoryRepository.save(newCategory);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, String name) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    category.setName(name);
                    return categoryRepository.save(category);
                })
                .map(updatedCategory -> categoryMapper.toResponse(updatedCategory))
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }


    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.findById(categoryId)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> { throw new NotFoundException("Category not found"); }
                );

    }
}
