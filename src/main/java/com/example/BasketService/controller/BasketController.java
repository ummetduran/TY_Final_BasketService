package com.example.BasketService.controller;

import com.example.BasketService.models.dto.UserDTO;
import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.service.IBasketService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("basket")
public class BasketController {
    private final IBasketService basketService;
    private RestTemplateBuilder restTemplateBuilder;
    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public ResponseEntity<?> addBasket(@RequestBody Basket basket){
        return basketService.addBasket(basket);
    }


    @GetMapping("/{productId}")
    public ResponseEntity getAllUsersForProduct(@PathVariable Long productId){
       return new ResponseEntity(basketService.getAllUsersForProduct(productId), HttpStatus.OK);
    }
}
