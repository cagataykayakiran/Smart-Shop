package com.smartshop.service.customer;

import com.smartshop.dto.response.CustomerResponse;
import com.smartshop.exception.AlreadyExistsException;
import com.smartshop.exception.NotFoundException;
import com.smartshop.mapper.CustomerMapper;
import com.smartshop.model.Customer;
import com.smartshop.repository.CustomerRepository;
import com.smartshop.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CartService cartService;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> customerMapper.toResponse(customer))
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
    }

    @Override
    public CustomerResponse createCustomer(String firstName, String lastName, String email, String password) {
        return (CustomerResponse) customerRepository
                .findByEmail(email)
                .map(existingCustomer -> {
                    throw new AlreadyExistsException("Customer already exists with email: " + email);
                })
                .orElseGet(() -> {
                    var customer = Customer
                            .builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .password(password)
                            .build();
                    var newCustomer = customerRepository.save(customer);
                    cartService.initializeNewCart(newCustomer.getId());
                    return customerMapper.toResponse(newCustomer);
                });
    }

    @Override
    public CustomerResponse updateCustomer(Long id, String firstName, String lastName, String email, String password) {
        return customerRepository
                .findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setFirstName(firstName);
                    existingCustomer.setLastName(lastName);
                    existingCustomer.setEmail(email);
                    existingCustomer.setPassword(password);
                    var newCustomer = customerRepository.save(existingCustomer);
                    return customerMapper.toResponse(newCustomer);
                }).orElseThrow(() -> new NotFoundException("Customer not found with id: " + id));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.findById(id).ifPresentOrElse(
                customerRepository::delete,
                () -> { throw new NotFoundException("Customer not found with id: " + id); }
        );
    }

}
