package com.example.BasketService.repository;

import com.example.BasketService.models.entities.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface BasketRepository extends MongoRepository<Basket, String> {

    @Query("{'products.productId' : ?0}")
    List<Basket> findUsersIdByProductId(Long productId);


}
