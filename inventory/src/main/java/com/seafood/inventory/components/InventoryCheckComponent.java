package com.seafood.inventory.components;

import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.entities.dto.transaction.TransactionRequestDTO;
import com.seafood.inventory.inventory.event.InventoryCheckEvent;
import com.seafood.inventory.inventory.event.InventoryCheckRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryCheckComponent {

    private final CheckAndReserveInventoryComponent checkAndReserveInventoryComponent;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionProcessorComponent transactionProcessorComponent;

    @Autowired
    public InventoryCheckComponent(CheckAndReserveInventoryComponent checkAndReserveInventoryComponent, ApplicationEventPublisher eventPublisher, TransactionProcessorComponent transactionProcessorComponent) {
        this.checkAndReserveInventoryComponent = checkAndReserveInventoryComponent;
        this.eventPublisher = eventPublisher;
        this.transactionProcessorComponent = transactionProcessorComponent;
    }

    @Async
    public void processOrder(TransactionRequestDTO transactionDTO){
        /*InventoryResponsePayload responsePayload = new InventoryResponsePayload(
                transactionDTO.getTransactionId(),
                calculateAvailability(transactionDTO)
        );*/
        boolean isInventoryAvailable = transactionProcessorComponent.processTransaction(transactionDTO);
        log.info("Is inventory available: {}", isInventoryAvailable);

        InventoryResponsePayload inventoryResponsePayload = new InventoryResponsePayload(
                transactionDTO.getTransactionId(),
                isInventoryAvailable
        );
        log.info("Sending order: {}, back to the Transaction Service", inventoryResponsePayload);
        eventPublisher.publishEvent(new InventoryCheckEvent(inventoryResponsePayload));
    }

    public boolean calculateAvailability(TransactionRequestDTO transactionDTO) {
        //create component to calculate from som sort of availability calculator
        return false;
    } // done

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
