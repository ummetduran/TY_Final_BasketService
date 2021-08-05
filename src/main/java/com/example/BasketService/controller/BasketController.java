package com.example.BasketService.controller;

import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.service.IBasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("basket")
public class BasketController {
    private final IBasketService basketService;

    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public ResponseEntity<?> addBasket(@RequestBody Basket basket){
        return new ResponseEntity<>(basketService.addBasket(basket), HttpStatus.CREATED);
    }
}
