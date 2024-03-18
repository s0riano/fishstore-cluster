package com.seafood.inventory.inventory.operations.addCatch;

import com.seafood.inventory.entities.dto.CatchDTO;
import com.seafood.inventory.inventory.InventoryService;
import com.seafood.inventory.sellercatch.Catch;
import com.seafood.inventory.sellercatch.CatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddCatchToInventoryHandler {

    private final CatchRepository catchRepository;

    @Autowired
    public AddCatchToInventoryHandler(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }

    public void handle(UUID storeId, List<CatchDTO> catchDTOs) {
        log.info("Adding catches to inventory for shop ID: " + storeId);
        List<Catch> catches = catchDTOs.stream()
                .map(dto -> convertDTOToCatch(dto, storeId))
                .collect(Collectors.toList());

        catchRepository.saveAll(catches);
    }

    private Catch convertDTOToCatch(CatchDTO dto, UUID storeId) {
        Catch newCatch = new Catch();
        newCatch.setShopId(storeId);
        newCatch.setCatchId(dto.getCatchId());
        newCatch.setSeafoodType(dto.getSeafoodType());
        newCatch.setKilos(dto.getTotalKilos());
        // Set other necessary fields like dateCaught, expiryDate, etc.
        newCatch.setDateCaught(dto.getDateCaught().toLocalDate());
        return newCatch;
    }
}
