package com.example.BasketService.service;

import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.Basket;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface IBasketService {
    ResponseEntity<?> addBasket(Basket basket);



   List<Long> getAllUsersForProduct(Long productId);

    void createUserInfoMessageToUserService(ProductDTO product);
}
