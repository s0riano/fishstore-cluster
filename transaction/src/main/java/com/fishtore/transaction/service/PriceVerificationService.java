package com.fishtore.transaction.service;

import com.fishtore.transaction.dto.PriceEntryDTO;
import com.fishtore.transaction.transaction.PriceStatus;

import java.util.List;
import java.util.UUID;

public interface PriceVerificationService {
    PriceStatus verifyPrices(List<PriceEntryDTO> priceEntries, UUID shopId);
}