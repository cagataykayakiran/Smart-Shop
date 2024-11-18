package com.smartshop.controller;

import com.smartshop.common.ApiResponse;
import com.smartshop.dto.request.AddProductRequest;
import com.smartshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(products)
                        .message("Products retrieved successfully")
                        .build()
        );
    }

    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        var product = productService.getProductById(productId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(product)
                        .message("Product retrieved successfully")
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
        log.info("Request: {}", request);
        var product = productService.addProduct(
                request.name(),
                request.price(),
                request.stock(),
                request.categoryName()
        );
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(product)
                        .message("Product added successfully")
                        .build()
        );
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody AddProductRequest request) {
        var product = productService.updateProduct(
                productId,
                request.name(),
                request.price(),
                request.stock()
        );
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(product)
                        .message("Product updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Product deleted successfully")
                        .build()
        );
    }

    @GetMapping("/product/{categoryName}/products")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String categoryName) {
        var products = productService.getProductsByName(categoryName);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(products)
                        .message("Products retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String name) {
        var products = productService.getProductsByCategory(name);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(products)
                        .message("Products retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/{productId}/price-history")
    public ResponseEntity<ApiResponse> getProductPriceHistory(@PathVariable Long productId) {
        var priceHistory = productService.getProductPriceHistory(productId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(priceHistory)
                        .message("Product price history retrieved successfully")
                        .build()
        );
    }
}
