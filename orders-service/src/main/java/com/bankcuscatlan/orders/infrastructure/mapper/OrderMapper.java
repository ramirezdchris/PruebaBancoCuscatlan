package com.bankcuscatlan.orders.infrastructure.mapper;


import com.bankcuscatlan.orders.model.dto.response.ResponseOrderDTO;
import com.bankcuscatlan.orders.model.dto.request.RequestOrderDTO;
import com.bankcuscatlan.orders.model.entity.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        uses = OrderDetailMapper.class
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "items", source = "products")

    Order toEntity(RequestOrderDTO dto);


    @Mapping(target = "products", source = "items")
    RequestOrderDTO toDto(Order entity);

    @Mapping(target = "products", source = "items")
    @Mapping(target = "orderid", source = "id")
    @Mapping(target = "customerid", source = "customerId")
    ResponseOrderDTO toResponseDto(Order entity);

    @AfterMapping
    default void SetIdOrderDetails(@MappingTarget Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
    }
}
