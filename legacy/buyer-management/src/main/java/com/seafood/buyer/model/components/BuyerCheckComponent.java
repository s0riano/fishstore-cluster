package com.seafood.buyer.model.components;

import com.seafood.buyer.model.entity.Buyer;
import com.seafood.buyer.model.entity.BuyerToSellerRequestDTO;
import com.seafood.buyer.model.enums.UserStatus;
import com.seafood.buyer.model.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BuyerCheckComponent {

    private final BuyerService buyerService;

    @Autowired
    public BuyerCheckComponent(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    public void modifyBuyerForTransitioningToSeller(BuyerToSellerRequestDTO buyerToSellerRequestDTO){
        try {
            Buyer buyer = buyerService.getBuyerById(buyerToSellerRequestDTO.getSellerId());

            if (buyer != null && buyer.getStatus() == UserStatus.ACTIVE) {
                buyer.setStatus(UserStatus.DEACTIVATED);
                Buyer updatedBuyer = buyerService.save(buyer);
                log.info("Buyer with ID {} has been deactivated. His status: {}", updatedBuyer.getId(), updatedBuyer.getStatus() );
                //send rabbit respons back to seller service about if the seller was activated or not
            } else {
                //log.warn("Buyer with ID {} either does not exist or is already inactive.", buyerToSellerRequestDTO.getSellerId());
                assert buyer != null;
                log.warn("User with ID {}, have the status ~> {}", buyer.getId(), buyer.getStatus());
            }
        } catch (Exception e) {
            log.error("Error occurred while trying to deactivate buyer: {}", e.getMessage());
        }
    }
}
