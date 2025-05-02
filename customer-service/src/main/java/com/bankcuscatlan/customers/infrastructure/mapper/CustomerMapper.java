package com.bankcuscatlan.customers.infrastructure.mapper;


import com.bankcuscatlan.customers.model.dto.customer.CustomerDTO;
import com.bankcuscatlan.customers.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firsname", source = "firstName")
    @Mapping(target = "lastname", source = "lastName")
    CustomerDTO toDTO(Customer customer);
}
