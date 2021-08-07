package com.example.BasketService.repository.BasketByProduct;

import com.example.BasketService.models.entities.basketproduct.BasketByProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketByProductRepository extends JpaRepository<BasketByProducts, Long> {

}
