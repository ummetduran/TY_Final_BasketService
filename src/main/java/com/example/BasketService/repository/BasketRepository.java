package com.example.BasketService.repository;

import com.example.BasketService.models.entities.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BasketRepository extends MongoRepository<Basket, String> {

}
