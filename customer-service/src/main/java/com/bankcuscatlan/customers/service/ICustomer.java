package com.bankcuscatlan.customers.service;


import com.bankcuscatlan.customers.model.dto.response.ResponseDTO;

public interface ICustomer {

    ResponseDTO getCustomerByID(Long id);
}
