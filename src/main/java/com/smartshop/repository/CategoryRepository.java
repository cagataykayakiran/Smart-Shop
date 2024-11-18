package com.smartshop.repository;

import com.smartshop.common.BaseRepository;
import com.smartshop.model.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
