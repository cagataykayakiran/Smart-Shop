package com.smartshop.service.customer;

import com.smartshop.dto.response.CustomerResponse;

public interface ICustomerService {

    CustomerResponse getCustomerById(Long id);
    CustomerResponse createCustomer(String firstName, String lastName, String email, String password);
    CustomerResponse updateCustomer(Long id, String firstName, String lastName, String email, String password);
    void deleteCustomer(Long id);
}
