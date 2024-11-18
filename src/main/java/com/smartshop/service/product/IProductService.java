package com.smartshop.service.product;

import com.smartshop.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {

    ProductResponse addProduct(String productName, BigDecimal price, int stock, String categoryName);
    ProductResponse updateProduct(Long id, String name, BigDecimal price, int stock);
    void deleteProduct(Long id);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getProductsByName(String name);
    List<ProductResponse> getProductsByCategory(String name);
}
