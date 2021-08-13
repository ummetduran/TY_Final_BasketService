package com.example.BasketService.amqp;

import com.example.BasketService.models.ProcessType;
import com.example.BasketService.models.dto.BasketProductsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ProductProcessMessage implements Serializable {
    BasketProductsDTO productsDTO;
    String type;
}
