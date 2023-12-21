package com.seafood.buyer.model.service;


import com.seafood.buyer.model.entity.Buyer;
import com.seafood.buyer.model.repository.BuyerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    public Buyer createBuyer(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public Buyer getBuyerById(UUID userId) {
        return buyerRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Buyer not found with ID: " + userId));
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

}

