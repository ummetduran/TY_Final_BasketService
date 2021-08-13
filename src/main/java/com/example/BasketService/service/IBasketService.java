package com.example.BasketService.service;

import com.example.BasketService.models.dto.BasketProductsDTO;
import com.example.BasketService.models.dto.DeleteProductDTO;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.Basket;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface IBasketService {
    ResponseEntity<?> addBasket(Basket basket);


    Basket addProductToBasket(Basket products, Long userId);
   List<Long> getAllUsersForProduct(Long productId, String type);



    Basket deleteProductFromBasket(DeleteProductDTO product, Long userId);
}
