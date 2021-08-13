package com.example.BasketService.amqp;

import com.example.BasketService.models.NotificationServiceMessageWrapper;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.service.IBasketService;
import com.example.BasketService.service.IBasketServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceListener {
    private final IBasketServiceImpl basketService;


    public NotificationServiceListener(IBasketServiceImpl basketService) {
        this.basketService = basketService;
    }

    @RabbitListener(queues = "${bs.rabbit.queues.notification-for-baskets}")
    public void notificationListener(ProductDTO message){
        System.out.println(message);

        basketService.getAllUsersForProduct(message.getProductId(), "PRICECHANGE");
    }

}
