package com.smartshop.service.product;

import com.smartshop.dto.response.ProductPriceHistoryResponse;
import com.smartshop.dto.response.ProductResponse;
import com.smartshop.exception.NotFoundException;
import com.smartshop.mapper.ProductHistoryMapper;
import com.smartshop.mapper.ProductMapper;
import com.smartshop.model.Category;
import com.smartshop.model.Product;
import com.smartshop.model.ProductPriceHistory;
import com.smartshop.repository.CategoryRepository;
import com.smartshop.repository.ProductPriceHistoryRepository;
import com.smartshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductPriceHistoryRepository productPriceHistoryRepository;
    private final ProductMapper productMapper;
    private final ProductHistoryMapper productHistoryMapper;

    @Override
    public ProductResponse addProduct(String productName, BigDecimal price, int stock, String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
        return addProduct(productName, price, stock, category);
    }

    private ProductResponse addProduct(String name, BigDecimal price, int stock, Category category) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .category(category)
                .build();
        productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, String name, BigDecimal price, int stock) {
        return productRepository.findById(id)
                .map(existingProduct -> updateProduct(existingProduct, name, price, stock))
                .map(updatedProduct -> productMapper.toResponse(updatedProduct))
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    private Product updateProduct(Product existingProduct, String name, BigDecimal price, int stock) {
        if (!existingProduct.getPrice().equals(price)) {
            saveProductPriceHistory(existingProduct);
        }

        existingProduct.setName(name);
        existingProduct.setPrice(price);
        existingProduct.setStock(stock);
        return productRepository.save(existingProduct);
    }

    private void saveProductPriceHistory(Product product) {
        ProductPriceHistory history = ProductPriceHistory.builder()
                .product(product)
                .price(product.getPrice())
                .effectiveDate(LocalDateTime.now())
                .build();
        productPriceHistoryRepository.save(history);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> { throw new NotFoundException("Product not found"); }
                );
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> productMapper.toResponse(product))
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> productMapper.toResponse(product)).toList();
    }

    @Override
    public List<ProductResponse> getProductsByName(String categoryName) {
        return productRepository.findByName(categoryName)
                .stream()
                .map(product -> productMapper.toResponse(product)).toList();
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .map(Category::getProducts)
                .map(products -> products.stream()
                        .map(product -> productMapper.toResponse(product))
                        .toList()
                )
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public List<ProductPriceHistoryResponse> getProductPriceHistory(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        return productPriceHistoryRepository.findByProductIdOrderByEffectiveDateDesc(productId)
                .stream()
                .map(history -> productHistoryMapper.toResponse(history))
                .collect(Collectors.toList());
    }
}

