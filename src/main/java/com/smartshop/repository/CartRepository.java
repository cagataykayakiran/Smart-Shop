package com.smartshop.repository;

import com.smartshop.common.BaseRepository;
import com.smartshop.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart> {

    Optional<Cart> findByCustomerId(Long userId);

    boolean existsByCustomerId(Long customerId);
}
