package com.example.BasketService.service;

import com.example.BasketService.amqp.ProductCountProducer;
import com.example.BasketService.amqp.ProductProcessMessage;
import com.example.BasketService.amqp.UserIdForInfoProducer;
import com.example.BasketService.amqp.UserInfoForStockProducer;
import com.example.BasketService.models.UserInfoMessage;
import com.example.BasketService.models.dto.BasketInfo;
import com.example.BasketService.models.dto.BasketProductsDTO;
import com.example.BasketService.models.dto.DeleteProductDTO;
import com.example.BasketService.models.dto.UserInfoForStockMessage;
import com.example.BasketService.models.entities.Basket;
import com.example.BasketService.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class IBasketServiceImpl implements IBasketService {

    private final BasketRepository basketRepository;
    private final UserIdForInfoProducer userInfoProducer;
    private final ProductCountProducer productCountProducer;
    private final UserInfoForStockProducer userInfoForStockProducer;


    @Autowired
    public IBasketServiceImpl(BasketRepository basketRepository, UserIdForInfoProducer userInfoProducer, ProductCountProducer productCountProducer, UserInfoForStockProducer userInfoForStockProducer) {
        this.basketRepository = basketRepository;
        this.userInfoProducer = userInfoProducer;
        this.productCountProducer = productCountProducer;
        this.userInfoForStockProducer = userInfoForStockProducer;
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

            if(index != -1)
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
        double kargo =0;
        if (araToplam<100){
            kargo = 4.99;
        }else{
            kargo=0;
        }
        BasketInfo info = new BasketInfo(araToplam, kargo);
        basket.setBasketInfo(info);
        basketRepository.deleteByUserId(userId);
        basketRepository.save(basket);
        System.out.println(basket);
        BasketProductsDTO bpdto = products.getProducts().get(0);
        ProductProcessMessage message = new ProductProcessMessage(bpdto, "ADD");
        productCountProducer.sendToQueue(message);
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
        double kargo =0;
        if (araToplam<100){
            kargo = 4.99;
        }else{
            kargo=0;
        }
        BasketInfo info = new BasketInfo(araToplam, kargo);
        basket.setBasketInfo(info);
        basket.setProducts(productInBasket);
        basketRepository.deleteByUserId(userId);
        basketRepository.save(basket);
        BasketProductsDTO deletedProduct = new BasketProductsDTO();
        deletedProduct.setProductId(product.getProductId());
        deletedProduct.setQuantityInBasket(product.getCount());
        ProductProcessMessage message = new ProductProcessMessage(deletedProduct, "DELETE");
        productCountProducer.sendToQueue(message);
        return basket;
    }


    public List<Long> getAllUsersForProduct(Long productId, String type) {
        List<Basket> basketList = new ArrayList<Basket>();
        List<Long> userIdList = new ArrayList<Long>();
        basketList = basketRepository.findUsersIdByProductId(productId);
        for (Basket basket : basketList) {
            userIdList.add(basket.getUserId());
        }

        UserInfoMessage message = new UserInfoMessage();
        message.setUserIdList(userIdList);
        System.out.println(message);
        if(type.equalsIgnoreCase("PRICECHANGE")) {
            userInfoProducer.sendToQueue(message);
        }else if (type.equalsIgnoreCase("3") ||type.equalsIgnoreCase("0") ){
            UserInfoForStockMessage stockmessage = new UserInfoForStockMessage();
            stockmessage.setUserIdList(userIdList);
            stockmessage.setProductId(productId);
            stockmessage.setMessageType(type);

            userInfoForStockProducer.sendToQueue(stockmessage);
            System.out.println(stockmessage);
        }

        return userIdList;
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
