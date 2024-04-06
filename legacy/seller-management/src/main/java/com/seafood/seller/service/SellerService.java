package com.seafood.seller.service;

import com.seafood.seller.components.BuyerToSellerTransferComponent;
import com.seafood.seller.enums.UserStatus;
import com.seafood.seller.event.SellerChangedEvent;
import com.seafood.seller.model.dto.BuyerToSellerDTO;
import com.seafood.seller.model.dto.ValidateSellerDTO;
import com.seafood.seller.model.entity.Seller;
import com.seafood.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerService {
    private RabbitTemplate rabbitTemplate;

    private SellerRepository sellerRepository;

    private BuyerToSellerTransferComponent transferComponent;


    @Autowired
    public SellerService(SellerRepository sellerRepository, BuyerToSellerTransferComponent transferComponent, RabbitTemplate rabbitTemplate) {
        this.sellerRepository = sellerRepository;
        this.transferComponent = transferComponent;
        this.rabbitTemplate = rabbitTemplate;
    }


    @RabbitListener(queues = "validate-seller-queue") //???
    public void validateSellerId(UUID sellerId) { //changed to uuid from long on this func, might crash?
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

    public Seller transferBuyerToSeller(BuyerToSellerDTO dto) {
        return transferComponent.transferBuyerToSeller(dto);
    }

    public void notifyBuyerServiceAboutSellerSetup(UUID buyerId) {
        rabbitTemplate.convertAndSend("sellerExchange", "sellerRoutingKey", buyerId);
    }

    @RabbitListener(queues = "sellerQueue")
    public void receiveBuyerServiceConfirmation(UUID buyerId) {
        // Logic to activate the seller account
    }

    public ValidateSellerDTO validateSeller(UUID sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + sellerId));
        // For now, manually setting status to ACTIVE
        return ValidateSellerDTO.builder()
                .userID(seller.getId())
                .status(UserStatus.ACTIVE) // Ideally, this should be seller.getStatus() in the future
                .build();
    }

}