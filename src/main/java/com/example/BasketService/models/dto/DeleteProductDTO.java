package com.example.BasketService.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteProductDTO implements Serializable {
    private Long productId;
    private int count;
}
