package com.fishtore.transaction.service;

import com.fishtore.transaction.components.ExternalPriceApiComponent;
import com.fishtore.transaction.dto.PriceEntryDTO;
import com.fishtore.transaction.transaction.PriceStatus;
import com.fishtore.transaction.transaction.enums.SeafoodType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PriceVerificationServiceImpl implements PriceVerificationService {

    private final ExternalPriceApiComponent externalPriceApiComponent;

    @Autowired
    public PriceVerificationServiceImpl(ExternalPriceApiComponent externalPriceApiComponent) {
        this.externalPriceApiComponent = externalPriceApiComponent;
    }

    @Override
    public PriceStatus verifyPrices(List<PriceEntryDTO> priceEntries, UUID shopId) {
        List<PriceEntryDTO> currentPrices = externalPriceApiComponent.getCurrentPrices(shopId);
        if (currentPrices == null) {
            log.error("API call to fetch current prices failed or returned null for seller {}", shopId);
            return PriceStatus.API_CALL_ISSUE;
        }

        log.info("THE ITEMS {}", currentPrices);

        for (PriceEntryDTO entry : priceEntries) {
            if (!EnumUtils.isValidEnum(SeafoodType.class, entry.getSeafoodType().name())) {
                log.error("Invalid seafood type: {}", entry.getSeafoodType());
                return PriceStatus.INVALID_SEAFOOD_TYPE;
            }

            if (entry.getPrice() != null && entry.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                log.error("Suspected tampering detected for seafood type {}", entry.getSeafoodType());
                return PriceStatus.SUSPECTED_TAMPERING;
            }

            PriceEntryDTO currentPrice = currentPrices.stream()
                    .filter(cp -> cp.getSeafoodType().equals(entry.getSeafoodType()))
                    .findFirst()
                    .orElse(null);

            if (currentPrice == null || currentPrice.getPrice().compareTo(entry.getPrice()) != 0) { //do not use .equals to in case of scale difference
                log.error("Error in verifying price");
                return PriceStatus.PRICE_MISMATCH;
            }
        }
        return PriceStatus.PRICE_VERIFIED_AND_MATCHES;
    }

    // using EnumUtils.isValidEnum instead
    private boolean isSeafoodTypeValid(SeafoodType seafoodType) {
        for (SeafoodType type : SeafoodType.values()) {
            if (type.equals(seafoodType)) {
                return true;
            }
        }
        return false;
    }
}


