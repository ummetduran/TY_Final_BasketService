package com.example.BasketService.amqp;

import com.example.BasketService.models.UserInfoMessage;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserIdForInfoProducer {

    @Value("${bs.rabbit.routing.name.user-service}")
    private String routingName;

    private final DirectExchange exchange;
    private final RabbitTemplate rabbitTemplate;

    public UserIdForInfoProducer(DirectExchange exchange, RabbitTemplate rabbitTemplate) {
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(UserInfoMessage message){
        rabbitTemplate.convertAndSend(exchange.getName(),routingName,message);
    }
}
