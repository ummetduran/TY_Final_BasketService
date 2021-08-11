package com.example.BasketService.controller;
import com.example.BasketService.models.dto.BasketProductsDTO;
import com.example.BasketService.models.dto.DeleteProductDTO;
import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.service.IBasketService;
import org.springframework.http.HttpStatus;
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


    @GetMapping("/{productId}")
    public ResponseEntity getAllUsersForProduct(@PathVariable Long productId){
       return new ResponseEntity(basketService.getAllUsersForProduct(productId), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity addProductToBasket(@RequestBody Basket products, @PathVariable Long userId){
        return new ResponseEntity(basketService.addProductToBasket(products, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteProductFromBasket(@RequestBody DeleteProductDTO product, @PathVariable Long userId){
        return new ResponseEntity(basketService.deleteProductFromBasket(product, userId), HttpStatus.OK);
    }
}
