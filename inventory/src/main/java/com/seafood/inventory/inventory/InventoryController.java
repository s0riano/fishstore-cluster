package com.seafood.inventory.inventory;


import com.seafood.inventory.enums.SeafoodType;
import com.seafood.inventory.sellercatch.CatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final CatchService catchService; // Assuming CatchService is the service that contains getInventoryBySellerId()

    @Autowired
    public InventoryController(CatchService catchService) {
        this.catchService = catchService;
    }

    @Autowired
    private InventoryService inventoryService;

}
