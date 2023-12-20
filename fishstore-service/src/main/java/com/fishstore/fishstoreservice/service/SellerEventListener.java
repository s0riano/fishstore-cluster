package com.fishstore.fishstoreservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SellerEventListener {

   /* @RabbitListener(queues = "sellerQueue")
    public void handleSellerChanged(SellerChangedEvent event) {
        // Update your local cache or database with the seller details
    }*/
}