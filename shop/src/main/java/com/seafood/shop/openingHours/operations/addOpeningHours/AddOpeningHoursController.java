package com.seafood.shop.openingHours.operations.addOpeningHours;

import com.seafood.shop.dto.openingHours.OpeningHoursDTO;
import com.seafood.shop.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/store")
public class AddOpeningHoursController {

    private final AddOpeningHoursHandler addOpeningHoursHandler;

    @Autowired
    public AddOpeningHoursController(AddOpeningHoursHandler addOpeningHoursHandler) {
        this.addOpeningHoursHandler = addOpeningHoursHandler;
    }

    @PostMapping("/opening-hours")
    public ResponseEntity<?> addOpeningHours(@RequestBody List<OpeningHoursDTO> openingHoursDTOs,
                                             Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID storeId = userDetails.getShopId();

        addOpeningHoursHandler.handle(storeId, openingHoursDTOs);
        return ResponseEntity.ok().build();
    }
}