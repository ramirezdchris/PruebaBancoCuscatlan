package com.bankcuscatlan.payment.service;


import com.bankcuscatlan.payment.model.dto.response.ResponseDTO;
import com.bankcuscatlan.payment.model.dto.response.ResponsePaymentDTO;
import com.bankcuscatlan.payment.model.request.RequestPaymentDTO;

public interface IPayment {

    ResponseDTO savePayment(RequestPaymentDTO requestPaymentDTO);

    ResponseDTO<ResponsePaymentDTO> getPayments(RequestPaymentDTO requestPaymentDTO);
}
