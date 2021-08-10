package com.example.BasketService.service;

import com.example.BasketService.amqp.Producer;
import com.example.BasketService.amqp.UserInfoMessage;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.repository.BasketRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class IBasketServiceImpl implements IBasketService{

    private final BasketRepository basketRepository;
    private final Producer producer;


    @Autowired
    public IBasketServiceImpl(BasketRepository basketRepository, Producer producer) {
        this.basketRepository = basketRepository;

        this.producer = producer;
    }

    @Override
    public ResponseEntity<?> addBasket(Basket basket) {

        return new ResponseEntity(basketRepository.insert(basket), HttpStatus.OK);
    }

    public List<Long> getAllUsersForProduct(Long productId){
        List<Basket> basketList = new ArrayList<Basket>();
        List<Long> userIdList = new ArrayList<Long>();
        basketList =basketRepository.findUsersIdByProductId(productId);
        for(Basket basket: basketList){
            userIdList.add(basket.getUserId());
        }
        System.out.println(userIdList);

        return userIdList;
    }

    @Override
    public void createUserInfoMessageToUserService(ProductDTO productDTO) {

        List<Long> userIdList = getAllUsersForProduct(productDTO.getProductId());
        System.out.println(userIdList.get(0));
    }


}
