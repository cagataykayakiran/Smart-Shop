package com.smartshop.repository;

import com.smartshop.common.BaseRepository;
import com.smartshop.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer> {

    Optional<Customer> findByEmail(String email);

}
