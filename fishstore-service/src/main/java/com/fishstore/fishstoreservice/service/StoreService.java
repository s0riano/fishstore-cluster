package com.fishstore.fishstoreservice.service;

import com.fishstore.fishstoreservice.model.entity.Store;
import com.fishstore.fishstoreservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StoreRepository storeRepository;

    public boolean validateSellerId(Long sellerId) {
        // Send message to validate ownerId
        rabbitTemplate.convertAndSend("validate-seller-queue", sellerId);

        // Listen for a response (for simplicity, using receiveAndConvert)
        Boolean isValid = (Boolean) rabbitTemplate.receiveAndConvert("seller-validation-response-queue");
        return isValid != null && isValid;
    }

    public void saveStore(Store store) {
        storeRepository.save(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}