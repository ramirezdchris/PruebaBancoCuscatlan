package com.bankcuscatlan.orders.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestOrderDTO {

    @NotNull(message = "customerId is required")
    @JsonProperty("customerid")
    private Long customerId;

    @NotEmpty(message = "At least one product is required")
    private List<@Valid RequestProductOrderDTO> products;

}
