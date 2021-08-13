package com.example.BasketService.amqp;

import com.example.BasketService.models.UserInfoMessage;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserIdForInfoProducer {

    @Value("${bs.rabbit.routing.name.user-service}")
    private String routingName;

    @Value("${bs.rabbit.exchange.name.user-service}")
    private String exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendToQueue(UserInfoMessage message){
        rabbitTemplate.convertAndSend(exchange,routingName,message);
    }
}
