package com.example.BasketService.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private  Long id;

    private String productName;

    private Double price;

    private Integer quantityInBasket;
}
