package com.bankcuscatlan.productservice.model.dto.fakeapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    public double rate;
    public int count;
}
