package com.seafood.shop.openingHours.operations.removeOpeningHours;

import com.seafood.shop.dto.openingHours.OpeningHoursDTO;
import com.seafood.shop.dto.openingHours.RemoveOpeningHourDTO;
import com.seafood.shop.openingHours.OpeningHours;
import com.seafood.shop.openingHours.OpeningHoursRepository;
import com.seafood.shop.shop.Shop;
import com.seafood.shop.shop.ShopService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RemoveOpeningHoursHandler {

    private final OpeningHoursRepository openingHoursRepository;

    @Autowired
    public RemoveOpeningHoursHandler(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }

    public void handle(List<RemoveOpeningHourDTO> removeDTOs) {
        try {
            for (RemoveOpeningHourDTO dto : removeDTOs) {
                log.info("Removing Opening Hour ID: " + dto.getId());
                openingHoursRepository.deleteById(dto.getId());
            }
        } catch (EntityNotFoundException e) {
            log.error("Error occurred, opening hours not found: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred: " + e.getMessage());
        }
    }
}
