package com.bankcuscatlan.productservice.controller;

import com.bankcuscatlan.productservice.model.dto.product.ResponseDTO;
import com.bankcuscatlan.productservice.service.IProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final IProducts iProducts;

    @GetMapping(path = "/health-check")
    public String healthCheck(){
        return "Products Service";
    }

    @GetMapping(path = "/product/{id}")
    public ResponseEntity<ResponseDTO> getProduct(@PathVariable Integer id){
        return new ResponseEntity<>(
                iProducts.getProductFromApi(id),
                HttpStatus.OK);
    }
}
