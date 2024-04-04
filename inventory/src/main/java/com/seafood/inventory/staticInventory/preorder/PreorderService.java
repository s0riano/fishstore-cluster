package com.seafood.inventory.staticInventory.preorder;

import dto.TransactionItemDTO;
import dto.preorder.PreOrderTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PreorderService {

    // Inject necessary dependencies
    private final PreorderRepository preorderRepository;

    public PreorderService(PreorderRepository preorderRepository) {
        this.preorderRepository = preorderRepository;
    }

    public void recordSale(UUID inventoryId, PreOrderTransactionDTO preOrderDto) {
        Preorder sale = new Preorder();
        sale.setPreorderId(UUID.randomUUID());
        sale.setTransactionId(preOrderDto.getTransactionId());
        sale.setInventoryId(inventoryId);
        sale.setSoldItems(convertToSaleItems(preOrderDto.getItems()));
        sale.setSaleDate(LocalDate.now());

        preorderRepository.save(sale);
    }

    private List<PreorderItems> convertToSaleItems(List<TransactionItemDTO> items) {
        List<PreorderItems.Item> saleItems = items.stream()
                .map(item -> new PreorderItems.Item(item.getSeafoodType(), item.getKilos().doubleValue()))
                .collect(Collectors.toList());

        return List.of(new PreorderItems(saleItems));
    }
}
