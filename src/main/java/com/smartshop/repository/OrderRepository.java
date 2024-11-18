package com.smartshop.repository;

import com.smartshop.common.BaseRepository;
import com.smartshop.model.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {

    List<Order> findAllByCustomerId(Long customerId);
}
