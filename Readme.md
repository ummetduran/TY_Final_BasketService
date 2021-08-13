
# Before Running
- Have docker installed and running
  Create rabbit-mq container if not created before
  docker run -it --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

  - Start the container
  - docker start rabbitmq

- DB Configuration
  - spring.data.mongodb.host=localhost
  -spring.data.mongodb.database=baskets


- End Points
    
  - (POST) Add Basket -> localhost:4443/basket
    - {
    "userId":3
    }
                                                       
  - (POST) Add Products to Basket -> localhost:4443/basket/3   ->  {userId}
    - {
  "products":[{
  "productId":3,
  "productName":"Product 3",
  "price":123,
  "quantityInBasket":1
  }
  ]
  }
                                                        
  - (DELETE) Delete Product in Basket -> localhost:4443/basket/5 -> {userId}
    - {
  "productId":3,
  "count":1
  }
                                                                
  - (GET) Get Users by Who Has this Product ->localhost:4443/basket/3 -> {proudctId} 
