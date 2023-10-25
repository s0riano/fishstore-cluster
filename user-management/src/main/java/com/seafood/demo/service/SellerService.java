package com.seafood.demo.service;

import com.seafood.demo.event.SellerChangedEvent;
import com.seafood.demo.model.entity.Seller;
import com.seafood.demo.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SellerRepository sellerRepository;

    @RabbitListener(queues = "validate-seller-queue")
    public void validateSellerId(Long sellerId) {
        boolean exists = sellerRepository.existsById(sellerId);
        rabbitTemplate.convertAndSend("seller-validation-response-queue", exists);
    }


    public void createSeller(Seller seller) {
        // ... logic to create seller

        // Publish event
        SellerChangedEvent event = new SellerChangedEvent();
        // ... set event details from seller
        rabbitTemplate.convertAndSend("sellerExchange", "seller.created", event);
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.fetchAllSellers();
    }
}