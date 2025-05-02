package com.bankcuscatlan.orders.infrastructure.mapper;

import com.bankcuscatlan.orders.model.dto.request.RequestOrderDTO;
import com.bankcuscatlan.orders.model.dto.request.RequestProductOrderDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDTO;
import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDetailDTO;
import com.bankcuscatlan.orders.model.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productId", source = "productid")
    OrderDetail toEntity(RequestProductOrderDTO dto);

    @Mapping(target = "id", source = "productId")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "productPrice")
    ResponseOrderDetailDTO toDto(OrderDetail entity);

    List<OrderDetail> toEntityList(List<RequestOrderDTO> dtos);
    List<ResponseOrderDTO> toDtoList(List<OrderDetail> entities);
}
