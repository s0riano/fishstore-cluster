package com.seafood.shop.legacy.management;

import com.seafood.shop.shop.Shop;
import com.seafood.shop.legacy.entity.ShopRole;
import com.seafood.shop.legacy.enums.Role;
import com.seafood.shop.shop.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ManagementService {

    private final ShopRepository shopRepository;

    @Autowired
    public ManagementService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void addAssistantToShop(UUID shopId, UUID assistantId, UUID ownerId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));

        boolean isAuthorized = shop.getOwners().stream() // Checking if the ownerId belongs to an owner or manager of the shop
                .anyMatch(owner -> owner.getUserId().equals(ownerId) &&
                        (owner.getRole() == Role.OWNER || owner.getRole() == Role.STAFF));

        if (!isAuthorized) {
            throw new RuntimeException("Not authorized to add an assistant");
        }

        ShopRole newAssistant = new ShopRole(UUID.randomUUID(), shop, assistantId, Role.ASSISTANT);
        shop.getOwners().add(newAssistant);
        try {
            shopRepository.save(shop);
        } catch (Exception e) {
            log.error("error in saving/adding new assistant, {}, in shop: {}. ShopId: {}", assistantId, shop.getShopName(), shopId);
            throw new RuntimeException("Failed to save the assistant: " + e.getMessage(), e);
        }
    }
}
