package com.example.BasketService.service;

import com.example.BasketService.models.entities.basket.Basket;
import org.springframework.http.ResponseEntity;

public interface IBasketService {
    ResponseEntity<?> addBasket(Basket basket);



    // ResponseEntity<?> getAllUsersForProduct(Long productId);
}
