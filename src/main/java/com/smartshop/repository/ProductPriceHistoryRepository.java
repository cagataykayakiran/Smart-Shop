package com.smartshop.repository;

import com.smartshop.common.BaseRepository;
import com.smartshop.model.ProductPriceHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceHistoryRepository extends BaseRepository<ProductPriceHistory> {

    List<ProductPriceHistory> findByProductIdOrderByEffectiveDateDesc(Long productId);
}
