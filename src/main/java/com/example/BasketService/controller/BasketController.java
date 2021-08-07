package com.example.BasketService.controller;

import com.example.BasketService.models.entities.basket.Basket;
import com.example.BasketService.service.IBasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("basket")
public class BasketController {
    private final IBasketService basketService;

    public BasketController(IBasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public ResponseEntity<?> addBasket(@RequestBody Basket basket){
        return basketService.addBasket(basket);
    }


/*    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllUsersForProduct(@PathVariable(value = "productId") final Long productId){
        ResponseEntity<?> allUsersForProduct = basketService.getAllUsersForProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(allUsersForProduct);
    }*/
}
