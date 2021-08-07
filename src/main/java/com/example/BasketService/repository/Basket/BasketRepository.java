package com.example.BasketService.repository.Basket;

import com.example.BasketService.models.entities.basket.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends MongoRepository<Basket, String> {

    //Optional<List<UserDTO>> findUsersByProductId(Long productId);

}
