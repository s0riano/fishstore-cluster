package com.fishtore.transaction.service;

import com.fishtore.transaction.dto.PriceEntryDTO;
import com.fishtore.transaction.transaction.PriceStatus;

import java.util.List;

public interface PriceVerificationService {
    PriceStatus verifyPrices(List<PriceEntryDTO> priceEntries, Long sellerId);
}