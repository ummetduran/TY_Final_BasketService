package com.example.BasketService.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserInfoForStockConfig {

    @Value("${bs.rabbit.queue.name.user-info-for-stock}")
    private String stockQueueName;

    @Value("${bs.rabbit.routing.name.user-info-for-stock}")
    private String stockRoutingName;
    @Value("${bs.rabbit.exchange.name.user-info-for-stock}")
    private String stockExchangeName;

    @Bean
    public Queue queue(){
        return new Queue(stockQueueName, true);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(stockExchangeName);
    }

    @Bean
    public Binding binding(final Queue queue, final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(stockRoutingName);
    }

}
