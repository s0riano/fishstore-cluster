package com.seafood.buyer.model.components;

import com.seafood.buyer.model.dto.BuyerDTO;
import com.seafood.buyer.model.entity.Buyer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class CreateBuyer {

    public Buyer handleBuyerCreation(BuyerDTO buyerDTO) {
        UUID buyerID = UUID.randomUUID();
        Buyer buyer = new Buyer();

        buyer.setId(buyerID);
        buyer.setFirstName(buyerDTO.getFirstName());
        buyer.setLastName(buyerDTO.getLastName());
        buyer.setEmail(buyerDTO.getEmail());
        buyer.setPassword(buyerDTO.getPassword());
        buyer.setPhoneNumber(buyerDTO.getPhoneNumber());

        return buyer;
    }
}
