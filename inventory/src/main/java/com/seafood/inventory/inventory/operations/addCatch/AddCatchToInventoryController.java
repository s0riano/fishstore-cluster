package com.seafood.inventory.inventory.operations.addCatch;

import com.seafood.inventory.entities.dto.CatchDTO;
import com.seafood.inventory.user.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/backend/inventory")
public class AddCatchToInventoryController {


    private final AddCatchToInventoryHandler addCatchToInventoryHandler;

    @Autowired
    public AddCatchToInventoryController(AddCatchToInventoryHandler addCatchToInventoryHandler) {
        this.addCatchToInventoryHandler = addCatchToInventoryHandler;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCatchToInventory(@RequestBody List<CatchDTO> DTO,
                                             Authentication authentication)
    {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID storeId = userDetails.getShopId();
        addCatchToInventoryHandler.handle(storeId, DTO);
        return ResponseEntity.ok().build();
    }
}
