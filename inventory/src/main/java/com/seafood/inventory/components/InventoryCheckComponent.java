package com.seafood.inventory.components;

import com.seafood.inventory.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.dto.transaction.TransactionRequestDTO;
import com.seafood.inventory.event.InventoryCheckEvent;
import com.seafood.inventory.event.InventoryCheckRequestEvent;
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


        //create component to calculate from som sort of availability calculator

        return false;
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
