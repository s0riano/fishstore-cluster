package com.seafood.inventory.staticInventory.preorder.operations.getPreorder;

import com.seafood.inventory.staticInventory.preorder.Preorder;
import com.seafood.inventory.staticInventory.preorder.PreorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PreorderHandler { //why the fuck did i make a handler?

    private final PreorderRepository repository;

    @Autowired
    public PreorderHandler(PreorderRepository repository) {
        this.repository = repository;
    }

    public Optional<Preorder> getPreorderById(UUID saleId) {
        return repository.findById(saleId);
    }
}