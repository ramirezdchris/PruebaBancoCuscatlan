package com.bankcuscatlan.customers.service.impl;


import com.bankcuscatlan.customers.infrastructure.exceptions.CustomerNotFoundException;
import com.bankcuscatlan.customers.infrastructure.mapper.CustomerMapper;
import com.bankcuscatlan.customers.infrastructure.repository.CustomerRepository;
import com.bankcuscatlan.customers.model.dto.response.ResponseDTO;
import com.bankcuscatlan.customers.model.entity.Customer;
import com.bankcuscatlan.customers.service.ICustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerImpl implements ICustomer {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public ResponseDTO getCustomerByID(Long id) {
        Optional<Customer> customer = customerRepository.getCustomerById(id);
        log.info("Search Customer: {}", id);
        if (customer.isPresent()) {
            log.info("Customer: {}", customer.get());
            return new ResponseDTO<>("OK", "SUCCESS", customerMapper.toDTO(customer.get()));
        } else {
            log.warn("Customer with id {} NOT FOUND", id);
            throw new CustomerNotFoundException("Customer not found");
        }


    }


}
