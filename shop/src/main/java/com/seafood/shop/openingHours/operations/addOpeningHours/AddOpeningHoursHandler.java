package com.seafood.shop.openingHours.operations.addOpeningHours;

import com.seafood.shop.dto.openingHours.OpeningHoursDTO;
import com.seafood.shop.openingHours.OpeningHours;
import com.seafood.shop.openingHours.OpeningHoursRepository;
import com.seafood.shop.shop.Shop;
import com.seafood.shop.shop.ShopService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddOpeningHoursHandler {

    private final OpeningHoursRepository openingHoursRepository;
    private final ShopService shopService;

    @Autowired
    public AddOpeningHoursHandler(OpeningHoursRepository openingHoursRepository, ShopService shopService) {
        this.openingHoursRepository = openingHoursRepository;
        this.shopService = shopService;
    }

    public void handle(UUID storeId, List<OpeningHoursDTO> openingHoursDTOs) { //TODO: implement the catches
        log.info("ShopId: " + storeId);
        try {
            Shop shop = shopService.findById(storeId);
            List<OpeningHours> openingHoursList = openingHoursDTOs.stream()
                    .map(dto -> convertToEntity(dto, shop))
                    .collect(Collectors.toList());

            log.info("Opening Hours: " + openingHoursList);
            openingHoursRepository.saveAll(openingHoursList);
        } catch (EntityNotFoundException e) {
            log.error("some error happened, seems like the shop was not found: "  + e);
        } catch (Exception e) {
            log.error("some error happened: "  + e);
        }
    }

    private OpeningHours convertToEntity(OpeningHoursDTO dto, Shop shop) {
        OpeningHours openingHours = new OpeningHours();

        openingHours.setId(UUID.randomUUID());
        openingHours.setDate(dto.getDate());
        openingHours.setStartTime(dto.getStartTime());
        openingHours.setEndTime(dto.getEndTime());
        openingHours.setNotes(dto.getNotes());//TODO: might add some logic if this is null
        openingHours.setShop(shop);
        return openingHours;
    }
}
