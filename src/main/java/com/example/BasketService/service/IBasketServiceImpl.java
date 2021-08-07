package com.example.BasketService.service;

import com.example.BasketService.amqp.Producer;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.basket.Basket;
import com.example.BasketService.models.entities.basketproduct.BasketByProducts;
import com.example.BasketService.repository.BasketByProduct.BasketByProductRepository;
import com.example.BasketService.repository.Basket.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Configuration
public class IBasketServiceImpl implements IBasketService{

    private final BasketRepository basketRepository;

    private final BasketByProductRepository basketByProductRepository;
    private final Producer producer;

    @Autowired
    public IBasketServiceImpl(BasketRepository basketRepository, BasketByProductRepository basketByProductRepository, Producer producer) {
        this.basketRepository = basketRepository;
        this.basketByProductRepository = basketByProductRepository;
        this.producer = producer;
    }

    //public IBasketServiceImpl(){}

    @Override
    public ResponseEntity<?> addBasket(Basket basket) {
        transleteBasketforDB(basket);
        return new ResponseEntity(basketRepository.insert(basket), HttpStatus.OK);
    }

    void transleteBasketforDB(Basket basket){
        BasketByProducts basketByProducts = new BasketByProducts();
        basketByProducts.setUserId(basket.getUserId());
        for(ProductDTO product: basket.getProducts()){
            basketByProducts.setProductId(product.getId());
           basketByProductRepository.save(basketByProducts);
        }
    }


}
