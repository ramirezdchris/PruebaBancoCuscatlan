package com.bankcuscatlan.orders.model.dto.response;

import com.bankcuscatlan.orders.model.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderDTO {

    private Long orderid;
    private Long customerid;
    private List<ResponseOrderDetailDTO> products;
}
