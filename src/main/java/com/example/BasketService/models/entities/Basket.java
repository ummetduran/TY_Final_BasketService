package com.example.BasketService.models.entities;


import com.example.BasketService.models.dto.BasketInfo;
import com.example.BasketService.models.dto.BasketProductsDTO;
import com.example.BasketService.models.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.json.JsonObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("baskets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Basket {
    @Id
    private Long userId;

    @Field("products")
    private List<BasketProductsDTO> products;

    @Field("basketInfo")
    private BasketInfo basketInfo;

}
