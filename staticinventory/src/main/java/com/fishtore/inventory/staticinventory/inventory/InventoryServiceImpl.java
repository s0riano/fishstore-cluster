package com.fishtore.inventory.staticinventory.inventory;

import com.fishtore.inventory.staticinventory.dto.PriceInfoDTO;
import com.fishtore.inventory.staticinventory.dto.TransactionDTO;
import com.fishtore.inventory.staticinventory.dto.TransactionItemDTO;
import com.fishtore.inventory.staticinventory.payload.TransactionMessagePayload;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null); // or throw an exception
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory); // JPA's save can also update
    }

    @Override
    public void processFetchItems(List<TransactionItemDTO> items) {

    }

    @Override
    public List<PriceInfoDTO> findPricesBySeller(Long sellerId) {
        List<Inventory> inventories = inventoryRepository.findBySellerId(sellerId);
        return inventories.stream()
                .map(inventory -> new PriceInfoDTO(inventory.getSeafoodType(), inventory.getPricePerKilo()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional // Ensures the operation is atomic
    public boolean checkAndReserveInventory(TransactionDTO transactionRequestDTO) {
        // Iterate over each item in the transaction request
        for (TransactionItemDTO item : transactionRequestDTO.getItems()) {
            // Convert the seafood type from String to the SeafoodType enum
            SeafoodType type;
            try {
                type = SeafoodType.valueOf(item.getSeafoodType().toUpperCase());
            } catch (IllegalArgumentException e) {
                // The provided seafood type is not valid
                return false;
            }

            // Fetch the inventory for the specific seller and seafood type
            Inventory inventory = inventoryRepository.findBySellerIdAndSeafoodType(
                    transactionRequestDTO.getSellerId(), type);

            // Check if there's enough inventory
            if (inventory == null || inventory.getKilos().compareTo(item.getKilos()) < 0) {
                // Not enough inventory or item not found, cannot proceed
                return false;
            }

            // Reserve the inventory by reducing the kilos
            inventory.setKilos(inventory.getKilos().subtract(item.getKilos()));
            inventoryRepository.save(inventory);
        }

        // All items have enough inventory, reservation successful
        return true;
    }

    @Override
    public Inventory findBySellerIdAndSeafoodType(Long sellerId, SeafoodType valueOf) {
        return null;
    }


    @Override
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "inventoryCheckQueue", durable = "true"),
            exchange = @Exchange(name = "inventoryExchange"),
            key = "inventoryCheck"))
    public void processInventoryCheck(TransactionMessagePayload payload) {
        Long transactionId = payload.getTransactionId();
        List<TransactionItemDTO> items = payload.getItems();

        boolean isAvailable = false; //this has to be a method, cant make it yet

        // Create response payload
        Map<String, Object> responsePayload = new HashMap<>();
        responsePayload.put("transactionId", transactionId);
        responsePayload.put("isAvailable", isAvailable);

        // Send the response
        rabbitTemplate.convertAndSend("transactionExchange", "inventoryResponse", responsePayload);
    }
}

