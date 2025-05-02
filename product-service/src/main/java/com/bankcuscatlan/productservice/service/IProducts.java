package com.bankcuscatlan.productservice.service;

import com.bankcuscatlan.productservice.model.dto.product.ResponseDTO;

public interface IProducts {

    ResponseDTO getProductFromApi(Integer id);
}
