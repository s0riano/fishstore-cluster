package com.fishtore.transaction.service;

import com.fishtore.transaction.transaction.PriceStatus;
import dto.PriceEntryDTO;

import java.util.List;
import java.util.UUID;

public interface PriceVerificationInterface {
    PriceStatus verifyPrices(List<PriceEntryDTO> priceEntries, UUID shopId);
}