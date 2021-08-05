package com.example.BasketService.service;

import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IBasketServiceImpl implements IBasketService{

    private final BasketRepository basketRepository;

    public IBasketServiceImpl(BasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }

    @Override
    public Basket addBasket(Basket basket) {
        return basketRepository.insert(basket);
    }
}
