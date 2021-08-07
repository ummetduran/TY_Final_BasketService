package com.example.BasketService;

import com.example.BasketService.repository.BasketByProduct.BasketByProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class})
@EnableJpaRepositories(repositoryBaseClass = BasketByProductRepository.class, basePackages="com.example.BasketService.models.entities.basketproduct")
@EnableMongoRepositories(basePackages= "com.example.BasketService.models.entities.basket")
public class BasketServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(BasketServiceApplication.class, args);
	}

}
