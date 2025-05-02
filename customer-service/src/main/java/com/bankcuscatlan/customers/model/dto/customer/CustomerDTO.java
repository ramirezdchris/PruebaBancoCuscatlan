package com.bankcuscatlan.customers.model.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    public Integer id;
    public String firsname;
    public String lastname;
    public Integer age;
    public LocalDate birthday;

}
