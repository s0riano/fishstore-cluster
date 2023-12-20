package com.seafood.inventory.inventory;

import com.seafood.inventory.components.InventoryComponent;
import com.seafood.inventory.components.VerifyShopComponent;
import com.seafood.inventory.dto.CompactInventoryDTO;
import com.seafood.inventory.dto.InventoryDTO;
import com.seafood.inventory.inventory.components.CompactInventoryComponent;
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.sellercatch.CatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryService {

    private CatchRepository catchRepository;
    private SaleRepository saleRepository;
    private final CompactInventoryComponent compactInventoryComponent;
    private final VerifyShopComponent verifyShopComponent;
    private final InventoryComponent inventoryComponent;

    @Autowired
    public InventoryService(CatchRepository catchRepository, SaleRepository saleRepository, CompactInventoryComponent compactInventoryComponent, InventoryComponent inventoryComponent, VerifyShopComponent verifyShopComponent) {
        this.catchRepository = catchRepository;
        this.saleRepository = saleRepository;
        this.compactInventoryComponent = compactInventoryComponent;
        this.inventoryComponent = inventoryComponent;
        this.verifyShopComponent = verifyShopComponent;
    }

    public InventoryDTO calculateInventoryForSeller(UUID shopId) {
        if(verifyShopComponent.verifyIfShopExist(shopId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"); //adsjust to another error or something thats prettier than a white wall
        }
        return inventoryComponent.calculateInventoryForUser(shopId);
    }

    public InventoryDTO availableInventoryForShop(UUID shopId) {
        if(!verifyShopComponent.verifyIfShopExist(shopId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found");
        }
        return inventoryComponent.calculateInventoryForUser(shopId);
    }

    public CompactInventoryDTO getCompactInventoryForShop(UUID shopId) {
        if (!verifyShopComponent.verifyIfShopExist(shopId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found");
        }
        return compactInventoryComponent.getCompactInventoryForShop(shopId);
    }
}
