package com.seafood.shop.openingHours.operations.removeOpeningHours;

import com.seafood.shop.dto.openingHours.RemoveOpeningHourDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/store/opening-hours")
public class RemoveOpeningHoursController {

    private final RemoveOpeningHoursHandler removeOpeningHoursHandler;

    @Autowired
    public RemoveOpeningHoursController(RemoveOpeningHoursHandler removeOpeningHoursHandler) {
        this.removeOpeningHoursHandler = removeOpeningHoursHandler;
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeOpeningHours(@RequestBody List<RemoveOpeningHourDTO> openingHoursDTOs,
                                             Authentication authentication) {

        removeOpeningHoursHandler.handle(openingHoursDTOs);
        return ResponseEntity.ok().build();
    }

    //TODO: Add verification to check if the user sending the deletion is related to the store
}
