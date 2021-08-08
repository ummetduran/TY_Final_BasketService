package com.example.BasketService.service;

import com.example.BasketService.amqp.Producer;
import com.example.BasketService.amqp.UserInfoMessage;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.Basket;

import com.example.BasketService.repository.BasketRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Configuration
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
        UserInfoMessage message = UserInfoMessage.builder().id(UUID.randomUUID().toString()).message(userIdList).build();
        producer.sendToQueue(message);
        return userIdList;
    }


}
