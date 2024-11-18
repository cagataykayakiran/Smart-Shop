package com.smartshop.dto.request;


public record AddCustomerRequest(
        String firstname,
        String lastname,
        String email,
        String password
) { }
