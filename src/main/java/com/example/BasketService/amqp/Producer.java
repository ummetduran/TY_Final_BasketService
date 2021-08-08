package com.example.BasketService.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value("${bs.rabbit.routing.name}")
    private String routingName;


    @Value("${bs.rabbit.exchange.name}")
    private String exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(UserInfoMessage userInfoMessage){
        System.out.println(userInfoMessage);
        rabbitTemplate.convertAndSend(exchangeName, routingName, userInfoMessage);
    }

}
