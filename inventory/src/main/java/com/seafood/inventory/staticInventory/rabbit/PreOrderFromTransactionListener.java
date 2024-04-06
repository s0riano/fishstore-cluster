package com.seafood.inventory.staticInventory.rabbit;

import com.seafood.inventory.components.InventoryCheckComponent;
import com.seafood.inventory.components.TransactionProcessorComponent;
import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.staticInventory.Inventory;
import com.seafood.inventory.staticInventory.StaticInventoryRepository;
import com.seafood.inventory.staticInventory.Stock;
import com.seafood.inventory.staticInventory.event.InventoryPreorderCheckEvent;
import com.seafood.inventory.staticInventory.preorder.PreorderService;
import dto.TransactionItemDTO;
import dto.preorder.PreOrderTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
@Transactional
public class PreOrderFromTransactionListener {
    private final TransactionProcessorComponent transactionProcessorComponent;
    private final StaticInventoryRepository staticInventoryRepository;
    private final InventoryCheckComponent inventoryCheckComponent;
    private final PreorderService preorderService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public PreOrderFromTransactionListener(TransactionProcessorComponent transactionProcessorComponent,
                                           StaticInventoryRepository staticInventoryRepository,
                                           InventoryCheckComponent inventoryCheckComponent, PreorderService preorderService, ApplicationEventPublisher eventPublisher) {
        this.transactionProcessorComponent = transactionProcessorComponent;
        this.staticInventoryRepository = staticInventoryRepository;
        this.inventoryCheckComponent = inventoryCheckComponent;
        this.preorderService = preorderService;
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "${inventory.preorder.queue}")
    public void receiveMessage(PreOrderTransactionDTO dto) {
        log.info("Received transactionDTO: {}", dto);
        boolean isInventoryAvailable = processPreOrder(dto);
        log.info("Is inventory available: {}", isInventoryAvailable);

        InventoryResponsePayload inventoryResponsePayload = new InventoryResponsePayload(
                dto.getTransactionId(),
                isInventoryAvailable
        );

        log.info("Sending order: {}, back to the Transaction Service", inventoryResponsePayload);
        eventPublisher.publishEvent(new InventoryPreorderCheckEvent(inventoryResponsePayload));
    }

    private boolean processPreOrder(PreOrderTransactionDTO dto) {
        Optional<Inventory> inventoryOpt = staticInventoryRepository.findByInventoryIdAsString(dto.getInventoryId().toString());
        if (inventoryOpt.isEmpty()) {
            try { //TODO: this might be worked on later, since Inventory ID is string now
                Optional<Inventory> inventorySecondTry = staticInventoryRepository.findByInventoryId(dto.getInventoryId());
            } catch (Exception e) {
                log.warn("Inventory not found for ID: {}", dto.getInventoryId());
                return false;
            }
        }

        Inventory inventory = inventoryOpt.get();
        List<Stock> currentStock = inventory.getStock();

        for (TransactionItemDTO item : dto.getItems()) {

            Stock matchingStock = currentStock.stream() //Find matching stock
                    .filter(stock -> stock.getSeafoodType().equals(item.getSeafoodType()))
                    .findFirst()
                    .orElse(null);

            if (matchingStock == null || matchingStock.getRemainingKilos() < item.getKilos().doubleValue()) { //Check stock availability
                return false; // Not enough stock
            }

            matchingStock.deductKilos(item.getKilos().doubleValue()); //Deduct kilos from stock
        }

        //TODO: Add Function to call for double checking that the inventory is aligned (actually updated) can be a common function


        if (inventory.getListOfSaleIds() == null) {
            inventory.setListOfSaleIds(new ArrayList<>());
        }
        inventory.getListOfSaleIds().add(dto.getTransactionId());

        staticInventoryRepository.save(inventory);
        preorderService.recordSale(dto.getInventoryId(), dto);

        return true;
    }
}