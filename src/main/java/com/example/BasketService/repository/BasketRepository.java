package com.example.BasketService.repository;

import com.example.BasketService.models.dto.BasketProductsDTO;
import com.example.BasketService.models.entities.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface BasketRepository extends MongoRepository<Basket, String>{

    @Query("{'products.productId' : ?0}")
    List<Basket> findUsersIdByProductId(Long productId);

    @Query("{'userId' : ?0}")
    Basket findBasketByUserId(Long usersId);

    //@Query(value="{'userId' : $0", delete=true)
    void deleteByUserId(Long userId);


    //void addProductToBasket(BasketProductsDTO product, Long userId);


}
