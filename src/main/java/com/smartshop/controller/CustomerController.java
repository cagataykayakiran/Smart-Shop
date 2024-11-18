package com.smartshop.controller;

import com.smartshop.common.ApiResponse;
import com.smartshop.dto.request.AddCustomerRequest;
import com.smartshop.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}/customer")
    public ResponseEntity<ApiResponse> getCustomerById(@PathVariable Long customerId) {
        var customers = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(customers)
                        .message("Customers retrieved successfully")
                        .build()
        );
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody AddCustomerRequest request) {
        var customer = customerService.createCustomer(
                request.firstname(),
                request.lastname(),
                request.email(),
                request.password()
        );
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(customer)
                        .message("Customer created successfully")
                        .build()
        );
    }

    @PutMapping("/{customerId}/update")
    public ResponseEntity<ApiResponse> updateCustomer(@PathVariable Long customerId, @RequestBody AddCustomerRequest request) {
        var customer = customerService.updateCustomer(
                customerId,
                request.firstname(),
                request.lastname(),
                request.email(),
                request.password()
        );
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(customer)
                        .message("Customer updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{customerId}/delete")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Customer deleted successfully")
                        .build()
        );
    }
}
