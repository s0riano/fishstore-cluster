package com.seafood.shop.shop;

import com.seafood.shop.component.service.DisplayShopLandingPageComponent;
import com.seafood.shop.dto.shopDTOs.ShopLandingPageDTO;
import com.seafood.shop.legacy.entity.ShopRole;
import com.seafood.shop.legacy.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    private final DisplayShopLandingPageComponent displayShopLandingPageComponent;

    @Autowired
    public ShopService(ShopRepository shopRepository, DisplayShopLandingPageComponent displayShopLandingPageComponent) {
        this.shopRepository = shopRepository;
        this.displayShopLandingPageComponent = displayShopLandingPageComponent;
    }

    public Shop getShop(UUID shopId, UUID userId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        boolean isOwnerOrStaff = shop.getOwners().stream().anyMatch(owner -> owner.getUserId().equals(userId));

        if (!isOwnerOrStaff) {
            throw new RuntimeException("User does not have access to this shop");
        }

        return shop;
    }

    public List<ShopLandingPageDTO> getAllActiveShops() {
        return displayShopLandingPageComponent.getAllActiveShops();
    }

    public void addSellerToShop(UUID shopId, UUID sellerId, UUID ownerId, Role role) { //splice out to component
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));

        // Verify if the ownerId belongs to an owner of the shop
        shop.getOwners().stream()
                .filter(owner -> owner.getUserId().equals(ownerId) && owner.getRole() == Role.OWNER)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not authorized to add seller"));

        // Create a new ShopRole entity
        ShopRole newOwner = new ShopRole();
        newOwner.setId(UUID.randomUUID()); // Generate a new UUID
        newOwner.setShop(shop);
        newOwner.setUserId(sellerId);
        newOwner.setRole(role);

        // Save the new owner to the shop
        shop.getOwners().add(newOwner);
        shopRepository.save(shop);
    }

    public List<UUID> getAllActiveShopIds() {
        return displayShopLandingPageComponent.getAllActiveShops().stream()
                .map(ShopLandingPageDTO::getId)
                .collect(Collectors.toList());
    }

    public Shop updateStoreDescription(UUID storeId, String newDescription) {
        Shop shop = shopRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));
        shop.setDescription(newDescription);
        return shopRepository.save(shop);
    }

    public Shop findById(UUID shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with ID: " + shopId));
    }
}