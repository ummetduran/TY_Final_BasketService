
package com.example.BasketService.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgreEntityManager",
        transactionManagerRef = "postgreTransactionManager",
        basePackages="com.example.BasketService.models.entities.basketproduct"
)
public class PostgreConfig {
    @Bean(name = "postgreEntityManager")
    public LocalContainerEntityManagerFactoryBean getPostgreEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("postgreDataSource") DataSource postgreDataSource ){
        return builder.dataSource(postgreDataSource).packages("com.example.BasketService.models.entities.basketproduct").
                persistenceUnit("BasketByPorducts").properties(additionalJpaProperties()).build();
    }

    private Map<String,?> additionalJpaProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.hbm2ddl.auto", "create");
        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        map.put("hibernate.show_sql","true");
        return map;
    }


    @Bean("postgreDataSourceProperties")
    @Primary
    @ConfigurationProperties("app.datasource.postgre")
    public DataSourceProperties postgreDataSourceProperties(){
        return new DataSourceProperties();
    }



    @Bean("postgreDataSource")
    @Primary
    @ConfigurationProperties("app.datasource.servers")
    public DataSource serversDataSource(@Qualifier("postgreDataSourceProperties") DataSourceProperties serversDataSourceProperties) {
        return postgreDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgreTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("psotgreEntityManager") EntityManagerFactory postgreEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(postgreEntityManager);

        return transactionManager;
    }

}

