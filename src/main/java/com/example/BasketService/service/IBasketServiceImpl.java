package com.example.BasketService.service;

import com.example.BasketService.amqp.Producer;
import com.example.BasketService.amqp.UserIdForInfoProducer;
import com.example.BasketService.amqp.UserInfoMessage;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class IBasketServiceImpl implements IBasketService{

    private final BasketRepository basketRepository;
    private final UserIdForInfoProducer producer;



    @Autowired
    public IBasketServiceImpl(BasketRepository basketRepository, UserIdForInfoProducer producer) {
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

        UserInfoMessage message = new UserInfoMessage();
        message.setUserIdList(userIdList);
        System.out.println(message);
        producer.sendToQueue(message);
        return userIdList;
    }

    @Override
    public void createUserInfoMessageToUserService(ProductDTO productDTO) {

        List<Long> userIdList = getAllUsersForProduct(productDTO.getProductId());
        System.out.println(userIdList.get(0));
    }


}
