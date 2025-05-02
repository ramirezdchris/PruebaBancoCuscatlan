package com.bankcuscatlan.payment.infrastructure.mapper;

import com.bankcuscatlan.payment.model.dto.response.ResponsePaymentDTO;
import com.bankcuscatlan.payment.model.entity.Payment;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    Payment toEntity(RequestPaymentDTO dto);
    ResponsePaymentDTO toResponse(Payment entity);
}
