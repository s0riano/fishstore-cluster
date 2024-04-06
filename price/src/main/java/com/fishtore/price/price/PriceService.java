package com.fishtore.price.price;

import com.fishtore.price.dto.UpdatePriceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    public Optional<Price> getPricesById(String shopId) {
        return priceRepository.findById(shopId);
    }

    public Price updatePrices(UpdatePriceDTO dto) {
        return priceRepository.findById(dto.getId())
                .map(existingPrice -> {
                    existingPrice.setPrices(dto.getPrices());
                    return priceRepository.save(existingPrice);
                })
                .orElseThrow(() -> new RuntimeException("Price information not found for shop ID: " + dto.getId()));
    }
}
