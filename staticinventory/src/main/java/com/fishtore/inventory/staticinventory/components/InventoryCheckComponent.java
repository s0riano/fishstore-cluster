package com.fishtore.inventory.staticinventory.components;

import com.fishtore.inventory.staticinventory.dto.TransactionItemDTO;
import com.fishtore.inventory.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.inventory.staticinventory.dto.payload.InventoryResponsePayload;
import com.fishtore.inventory.staticinventory.event.InventoryCheckEvent;
import com.fishtore.inventory.staticinventory.event.InventoryCheckRequestEvent;
import com.fishtore.inventory.staticinventory.inventory.Inventory;
import com.fishtore.inventory.staticinventory.inventory.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InventoryCheckComponent {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckComponent.class);
    private final InventoryService inventoryService;
    private final ApplicationEventPublisher eventPublisher;
   /* private final PlatformTransactionManager transactionManager;

    // Constructor injection of the transaction manager
    public InventoryCheckComponent(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }*/
    @Autowired
    public InventoryCheckComponent(InventoryService inventoryService, ApplicationEventPublisher eventPublisher) {
        this.inventoryService = inventoryService;
        this.eventPublisher = eventPublisher;
    }

    @Async
    public void processOrder(TransactionRequestDTO transactionDTO){

        InventoryResponsePayload responsePayload = new InventoryResponsePayload(
                transactionDTO.getTransactionId(),
                calculateAvailability(transactionDTO)
        );
        logger.info("Sending order: {}, back to the Transaction Service", responsePayload);
        //need to make a function to remove the items that were sold
        eventPublisher.publishEvent(new InventoryCheckEvent(responsePayload));
    }

    public boolean calculateAvailability(TransactionRequestDTO transactionDTO) {
        for (TransactionItemDTO item : transactionDTO.getItems()) {
            Inventory inventory = inventoryService.findBySellerIdAndSeafoodType(
                    transactionDTO.getSellerId(), item.getSeafoodType());

            // If inventory is null or not enough, return false immediately
            if (inventory == null || inventory.getKilos().compareTo(item.getKilos()) < 0) {
                return false;
            }
        }

        return true;
    }

    @EventListener// Listen to the InventoryCheckRequestEvent and process the order
    public void onInventoryCheckRequestEvent(InventoryCheckRequestEvent event) {
        processOrder(event.transactionDTO());
    }

    /*
        The reason for the event listeners are that earlier the rabbitservice
        and a component depended on each other which wasn't good. I then decided to
        switch to an event driven architecture for this part so things would be safer.

    */
}
