package com.example.BasketService.amqp;

import com.example.BasketService.models.dto.StockDTO;
import com.example.BasketService.service.IBasketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Dictionary;
import java.util.List;

@Component
public class ProductStockListener {
    private final IBasketService basketService;

    public ProductStockListener(IBasketService basketService) {
        this.basketService = basketService;
    }

    @RabbitListener(queues = "${bs.rabbit.queues.product-quantity}")
    public void listenProductStock(StockDTO stockDTO){
        String type="";
        System.out.println(stockDTO);
        if(stockDTO.getQuantity()!=0){
            type="3";
        }else{
            type="0";
        }
        List<Long> userList =basketService.getAllUsersForProduct(stockDTO.getProductId(), type);
        System.out.println(userList);

    }
}
