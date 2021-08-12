package com.example.BasketService.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketProductsDTO implements Serializable {

    private Long productId;

    private String productName;

    private Double price;

    private Integer quantityInBasket;
}
