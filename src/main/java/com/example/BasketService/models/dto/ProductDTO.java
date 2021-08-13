package com.example.BasketService.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {

    private  Long productId;

    private Double oldPrice;

    private Double newPrice;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                '}';
    }
}
