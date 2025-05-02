package com.bankcuscatlan.customers.controller;


import com.bankcuscatlan.customers.model.dto.response.ResponseDTO;
import com.bankcuscatlan.customers.service.ICustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final ICustomer customer;

    @GetMapping(path = "/health-check")
    public String healthCheck(){
        return "Products";
    }

    @GetMapping(path = "/customer/{id}")
    public ResponseEntity<ResponseDTO> getProduct(@PathVariable Long id){
        return new ResponseEntity<>(
                customer.getCustomerByID(id),
                HttpStatus.OK);
    }


}
