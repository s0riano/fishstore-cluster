    package com.seafood.inventory.staticInventory.rabbit;

    import com.seafood.inventory.components.InventoryCheckComponent;
    import com.seafood.inventory.components.TransactionProcessorComponent;
    import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;
    import com.seafood.inventory.entities.enums.PreOrderTransactionDTO;
    import com.seafood.inventory.entities.enums.TransactionItemDTO;
    import com.seafood.inventory.inventory.event.InventoryCheckEvent;
    import com.seafood.inventory.staticInventory.Inventory;
    import com.seafood.inventory.staticInventory.InventoryRepository;
    import com.seafood.inventory.staticInventory.Stock;
    import com.seafood.inventory.staticInventory.preorder.PreorderService;
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
        private final InventoryRepository inventoryRepository;
        private final InventoryCheckComponent inventoryCheckComponent;
        private final PreorderService preorderService;
        private final ApplicationEventPublisher eventPublisher;

        @Autowired
        public PreOrderFromTransactionListener(TransactionProcessorComponent transactionProcessorComponent,
                                               InventoryRepository inventoryRepository,
                                               InventoryCheckComponent inventoryCheckComponent, PreorderService preorderService, ApplicationEventPublisher eventPublisher) {
            this.transactionProcessorComponent = transactionProcessorComponent;
            this.inventoryRepository = inventoryRepository;
            this.inventoryCheckComponent = inventoryCheckComponent;
            this.preorderService = preorderService;
            this.eventPublisher = eventPublisher;
        }

        @RabbitListener(queues = "${inventory.check.queue}")
        public void receiveMessage(PreOrderTransactionDTO dto) {
            log.info("Received transactionDTO: {}", dto);
            boolean isInventoryAvailable = processPreOrder(dto);
            log.info("Is inventory available: {}", isInventoryAvailable);

            InventoryResponsePayload inventoryResponsePayload = new InventoryResponsePayload(
                    dto.getTransactionId(),
                    isInventoryAvailable
            );

            log.info("Sending order: {}, back to the Transaction Service", inventoryResponsePayload);
            eventPublisher.publishEvent(new InventoryCheckEvent(inventoryResponsePayload));
        }

        private boolean processPreOrder(PreOrderTransactionDTO dto) {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(dto.getInventoryId());
            if (inventoryOpt.isEmpty()) {
                log.warn("Inventory not found for ID: {}", dto.getInventoryId());
                return false;
            }

            Inventory inventory = inventoryOpt.get();
            List<Stock> currentStock = inventory.getStock();

            for (TransactionItemDTO item : dto.getItems()) {
                // Find matching stock
                Stock matchingStock = currentStock.stream()
                        .filter(stock -> stock.getSeafoodType().equals(item.getSeafoodType()))
                        .findFirst()
                        .orElse(null);

                // Check stock availability
                if (matchingStock == null || matchingStock.getRemainingKilos() < item.getKilos().doubleValue()) {
                    return false; // Not enough stock
                }

                // Deduct kilos from stock
                matchingStock.deductKilos(item.getKilos().doubleValue());
            }

            // Add transaction ID to inventory
            if (inventory.getListOfSaleIds() == null) {
                inventory.setListOfSaleIds(new ArrayList<>());
            }
            inventory.getListOfSaleIds().add(dto.getTransactionId());

            // Save updated inventory
            inventoryRepository.save(inventory);
            preorderService.recordSale(dto.getInventoryId(), dto);

            return true;
        }
    }
