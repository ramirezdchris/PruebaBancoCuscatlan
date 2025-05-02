package com.bankcuscatlan.orders.infrastructure.mapper;

import com.bankcuscatlan.orders.model.dto.product.ProductDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "message", source = "title")
    @Mapping(target = "quantity", constant = "0") // setear luego si se conoce
    ResponseOrderDetailDTO fromProductDTO(ProductDTO dto);
}
