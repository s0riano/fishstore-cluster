package com.seafood.demo.service;

import com.seafood.demo.model.dto.OwnerDetails;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @RabbitListener(queues = "validate-seller-queue")
    public void handleOwnerDetails(OwnerDetails ownerDetails) {
        // Process the owner details
    }
}
