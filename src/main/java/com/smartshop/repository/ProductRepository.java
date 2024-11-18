package com.smartshop.repository;

import com.smartshop.common.BaseRepository;
import com.smartshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

    List<Product> findByName(String name);
}
