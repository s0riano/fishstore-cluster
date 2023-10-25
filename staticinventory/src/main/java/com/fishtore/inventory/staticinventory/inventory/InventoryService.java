package com.fishtore.inventory.staticinventory.inventory;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "fetchItemsQueue", durable = "true"),
            exchange = @Exchange(name = "inventoryExchange"),
            key = "fetchItems"))
    public void processFetchItems(List<TransactionItemDTO> items) {
        // Fetch or validate the inventory items based on the received message

        // Once processing is done, send a response back to the TransactionService
        // This can be via another RabbitMQ message or any other mechanism you've set up
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null); // or throw an exception
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory); // JPA's save can also update
    }

}
