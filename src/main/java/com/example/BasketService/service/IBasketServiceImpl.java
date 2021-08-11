package com.example.BasketService.service;

import com.example.BasketService.amqp.Producer;
import com.example.BasketService.amqp.UserIdForInfoProducer;
import com.example.BasketService.amqp.UserInfoMessage;
import com.example.BasketService.models.dto.BasketInfo;
import com.example.BasketService.models.dto.BasketProductsDTO;
import com.example.BasketService.models.dto.DeleteProductDTO;
import com.example.BasketService.models.dto.ProductDTO;
import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class IBasketServiceImpl implements IBasketService {

    private final BasketRepository basketRepository;
    private final UserIdForInfoProducer producer;


    @Autowired
    public IBasketServiceImpl(BasketRepository basketRepository, UserIdForInfoProducer producer) {
        this.basketRepository = basketRepository;
        this.producer = producer;

    }

    @Override
    public ResponseEntity<?> addBasket(Basket basket) {

        return new ResponseEntity(basketRepository.insert(basket), HttpStatus.OK);
    }

    @Override
    public Basket addProductToBasket(Basket products, Long userId) {
        Basket basket = basketRepository.findBasketByUserId(userId);
        Double araToplam = 0.0;

        if (basket.getProducts() != null) {
            List<BasketProductsDTO> productInBasket = basket.getProducts();
            BasketProductsDTO prod = new BasketProductsDTO();
            prod.setProductId(products.getProducts().get(0).getProductId());
            prod.setProductName(products.getProducts().get(0).getProductName());
            prod.setPrice(products.getProducts().get(0).getPrice());

            int index = getIndexOf(productInBasket, products.getProducts().get(0).getProductId());
            System.out.println(index);
            prod.setQuantityInBasket(productInBasket.get(index).getQuantityInBasket());
            if (productInBasket.contains(prod)) {
                for (int i = 0; i < productInBasket.size(); i++) {
                    if (productInBasket.get(i).getProductId() == products.getProducts().get(0).getProductId()) {
                        int quantity = productInBasket.get(i).getQuantityInBasket();
                        quantity += products.getProducts().get(0).getQuantityInBasket();
                        productInBasket.get(i).setQuantityInBasket(quantity);
                    }
                }
            } else {
                productInBasket.add(products.getProducts().get(0));
            }
            for (BasketProductsDTO product : productInBasket) {
                araToplam += product.getPrice() * product.getQuantityInBasket();
            }
            basket.setProducts(productInBasket);

        } else {
            List<BasketProductsDTO> listOfAddProduct = new ArrayList<>();
            listOfAddProduct.addAll(products.getProducts());
            basket.setProducts(listOfAddProduct);

            for (BasketProductsDTO product : listOfAddProduct) {
                araToplam += product.getPrice() * product.getQuantityInBasket();
            }
        }
        BasketInfo info = new BasketInfo(araToplam, 5.99);
        basket.setBasketInfo(info);
        basketRepository.deleteByUserId(userId);
        basketRepository.save(basket);
        System.out.println(basket);
        return basket;
    }

    @Override
    public Basket deleteProductFromBasket(DeleteProductDTO product, Long userId) {
        Double araToplam = 0.0;
      Basket basket = basketRepository.findBasketByUserId(userId);
        List<BasketProductsDTO> productInBasket = basket.getProducts();
        int index = getIndexOf(productInBasket, product.getProductId());
        if(product.getCount()==productInBasket.get(index).getQuantityInBasket()){
            productInBasket.remove(index);
        }else if(product.getCount()<productInBasket.get(index).getQuantityInBasket()){
            productInBasket.get(index).setQuantityInBasket(productInBasket.get(index).getQuantityInBasket()-product.getCount());
        }

        for(BasketProductsDTO productDTO: productInBasket){
            araToplam += productDTO.getPrice()* productDTO.getQuantityInBasket();
        }
        BasketInfo info = new BasketInfo(araToplam, 5.99);
        basket.setBasketInfo(info);
        basket.setProducts(productInBasket);
        basketRepository.deleteByUserId(userId);
        basketRepository.save(basket);
        return basket;
    }

    public List<Long> getAllUsersForProduct(Long productId) {
        List<Basket> basketList = new ArrayList<Basket>();
        List<Long> userIdList = new ArrayList<Long>();
        basketList = basketRepository.findUsersIdByProductId(productId);
        for (Basket basket : basketList) {
            userIdList.add(basket.getUserId());
        }

        UserInfoMessage message = new UserInfoMessage();
        message.setUserIdList(userIdList);
        System.out.println(message);
        producer.sendToQueue(message);
        return userIdList;
    }

    @Override
    public void createUserInfoMessageToUserService(ProductDTO productDTO) {

        List<Long> userIdList = getAllUsersForProduct(productDTO.getProductId());
        System.out.println(userIdList.get(0));
    }



    public static int getIndexOf(List<BasketProductsDTO> list, Long productId) {
        int pos = 0;
        for (BasketProductsDTO productDTO : list) {
            if (productId == productDTO.getProductId())
                return pos;
            pos++;
        }
        return -1;
    }

}
