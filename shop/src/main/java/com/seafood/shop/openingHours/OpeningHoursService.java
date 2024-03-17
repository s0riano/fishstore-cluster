package com.seafood.shop.openingHours;

import com.seafood.shop.dto.openingHours.OpeningHoursDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;
    // If needed, inject other dependencies like ShopRepository

    @Autowired
    public OpeningHoursService(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }

    public void addOpeningHours(UUID storeId, List<OpeningHoursDTO> openingHoursDTOs) {
        List<OpeningHours> openingHoursList = openingHoursDTOs.stream()
                .map(dto -> convertToEntity(dto, storeId))
                .collect(Collectors.toList());

        openingHoursRepository.saveAll(openingHoursList);
    }

    private OpeningHours convertToEntity(OpeningHoursDTO dto, UUID storeId) {
        OpeningHours openingHours = new OpeningHours();
        // Set properties from dto to openingHours
        // Associate it with the store using storeId
        return openingHours;
    }
}
