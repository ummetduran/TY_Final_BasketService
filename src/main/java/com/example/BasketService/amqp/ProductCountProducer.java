package com.example.BasketService.amqp;

import com.example.BasketService.models.UserInfoMessage;
import com.example.BasketService.models.dto.BasketProductsDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductCountProducer {

    @Value("${bs.rabbit.routing.name.product-service}")
    private String routingName;


    @Value("${bs.rabbit.exchange.name.product-service}")
    private String exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(ProductProcessMessage message){
        System.out.println("Sended : "+ message);
        rabbitTemplate.convertAndSend(exchangeName, routingName, message);
    }

}
