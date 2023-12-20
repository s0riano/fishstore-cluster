package com.fishstore.fishstoreservice.service;

import com.fishstore.fishstoreservice.model.dto.SellerDetails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOwnerDetails(SellerDetails sellerDetails) {
        rabbitTemplate.convertAndSend("exchangeName", "routingKey", sellerDetails);
    }
}
