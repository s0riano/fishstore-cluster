package com.fishtore.price.service;

import com.fishtore.price.dto.PriceEntryDTO;
import com.fishtore.price.transaction.PriceStatus;

import java.util.List;

public interface PriceVerificationService {
    PriceStatus verifyPrices(List<PriceEntryDTO> priceEntries, Long sellerId);
}