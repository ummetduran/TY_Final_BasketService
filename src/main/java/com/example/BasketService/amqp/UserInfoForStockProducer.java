package com.example.BasketService.amqp;

import com.example.BasketService.models.UserInfoMessage;
import com.example.BasketService.models.dto.UserInfoForStockMessage;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserInfoForStockProducer {

    @Value("${bs.rabbit.routing.name.user-info-for-stock}")
    private  String routingName;

    @Value("${bs.rabbit.exchange.name.user-info-for-stock}")
    private  String exchangeName;

    @Autowired
    private  RabbitTemplate rabbitTemplate;


    public void sendToQueue(UserInfoForStockMessage message){

        rabbitTemplate.convertAndSend(exchangeName,routingName,message);
        System.out.println("Message : "+message);
    }
}
