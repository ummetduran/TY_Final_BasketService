package com.example.BasketService.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockDTO implements Serializable {
    private Long productId;
    private Integer quantity;
}
