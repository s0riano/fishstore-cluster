package com.seafood.seller.components;

import com.seafood.seller.model.dto.BuyerResponsDTO;
import com.seafood.seller.model.dto.BuyerToSellerDTO;
import com.seafood.seller.model.entity.Seller;
import com.seafood.seller.repository.SellerRepository;
import com.seafood.seller.service.RabbitMQService;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
public class BuyerToSellerTransferComponent {


    private final RestTemplate restTemplate; //create a config file
    private final SellerRepository sellerRepository;
    private final String buyerApiUrl;
    private final RabbitMQService rabbitMQService;


    @Autowired
    public BuyerToSellerTransferComponent(RestTemplate restTemplate, SellerRepository sellerRepository,
                                          @Value("${buyer.api.url}") String priceApiUrl, RabbitMQService rabbitMQService) {
        this.restTemplate = restTemplate;
        this.sellerRepository = sellerRepository;
        this.buyerApiUrl = priceApiUrl;
        this.rabbitMQService = rabbitMQService;
    }

    public Seller transferBuyerToSeller(BuyerToSellerDTO buyerToSellerDTO) { //make this void
        String url = String.format("%s/users/buyers/%s", buyerApiUrl, buyerToSellerDTO.getBuyerId());

        BuyerResponsDTO buyerApiRespons = null;
                //splice alot of this out to a new component
        try {
            buyerApiRespons = restTemplate.getForObject(url, BuyerResponsDTO.class);
            log.info("Buyer found: {}, Continuing the conversion to a Seller", buyerApiRespons);
        } catch (Exception e) {
            log.error("{} might not be online", buyerApiUrl);
            log.warn("buyerApiRespons: {}", buyerApiRespons);
            //log.error("Some Error..yea..this is a bad error message...took place when transferring Buyer to seller");
            throw new RuntimeException("Transfer failed", e);
        }

        if (buyerApiRespons != null) {
            UUID sellerId = buyerApiRespons.getId();
            log.info("the id: {}",sellerRepository.existsById(sellerId));
            //UUID realId = sellerRepository.existsById(sellerId); //is bool need to be adjusted accordingly
            if (sellerRepository.existsById(sellerId)) { //error taking place here
                log.error("User with ID {} already exists", buyerApiRespons.getId());
                throw new EntityExistsException("Seller already exists");
            }
        }

        Seller seller = new Seller();
        assert buyerApiRespons != null; //might want to handle error try catch?
        seller.setId(buyerApiRespons.getId());
        seller.setFirstName(buyerApiRespons.getFirstName());
        seller.setLastName(buyerApiRespons.getLastName());
        seller.setPassword(buyerApiRespons.getPassword());
        seller.setEmail(buyerApiRespons.getEmail());
        seller.setLastUpdated(LocalDateTime.now());

        if (seller.getId() == null){
            log.error("sellerID seems to have a fault, seller: {}", seller.getId());
            return null;
        }

        try{
            sellerRepository.save(seller);//Save the new seller to the repository
            //this cant be sent if there is something wrong with the seller, so think about that
            rabbitMQService.notifyBuyerServiceAboutTransferCompletion(seller.getId().toString());
        }catch (Exception e) {
            log.error("sellerRepository.save(seller); did not go as accordingly, seller: {}", seller.getId());
            throw new RuntimeException("Transfer failed", e);
        }

        //now being sent as a string, might want to refactor to be able to senda as UUID

        return seller;
    }
}
