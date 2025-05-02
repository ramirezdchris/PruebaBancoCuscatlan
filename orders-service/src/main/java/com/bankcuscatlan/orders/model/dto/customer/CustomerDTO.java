package com.bankcuscatlan.orders.model.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private int id;
    private String firsname;
    private String lastname;
    private Integer age;
    private LocalDate birthday;

}
