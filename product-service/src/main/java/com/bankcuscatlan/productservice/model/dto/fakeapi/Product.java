package com.bankcuscatlan.productservice.model.dto.fakeapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public int id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String image;
    public Rating rating;
}
