package com.seafood.shop.component;

import com.seafood.shop.dto.CreateShopDTO;
import com.seafood.shop.dto.user.ValidateSellerDTO;
import com.seafood.shop.entity.Shop;
import com.seafood.shop.entity.ShopRole;
import com.seafood.shop.enums.Role;
import com.seafood.shop.enums.UserStatus;
import com.seafood.shop.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class CreateNewShopComponent {

    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;
    private final String sellerApiUrl;

    @Autowired
    public CreateNewShopComponent(ShopRepository shopRepository,
                                  RestTemplate restTemplate,
                                  @Value("${external.seller.api.url}") String sellerApiUrl) {
        this.shopRepository = shopRepository;
        this.restTemplate = restTemplate;
        this.sellerApiUrl = sellerApiUrl;
    }

    public Shop createShop(UUID userId, CreateShopDTO shopDetailsDTO) {
        if (shopRepository.existsByOwnersUserId(userId)) {
            throw new RuntimeException("Seller already has a shop.");
        }

        ValidateSellerDTO sellerResponse = validateIfSellerExist(userId);

        if (sellerResponse.getStatus() != UserStatus.ACTIVE) {
            throw new RuntimeException("Seller with ID " + userId + " is not active.");
        }
        Shop shop = mapDtoToShop(shopDetailsDTO, userId);
        return shopRepository.save(shop);
    }

    private ValidateSellerDTO validateIfSellerExist(UUID userId) {
        String url = String.format("%s/sellers/validate/%s", sellerApiUrl, userId);
        try {
            return restTemplate.getForObject(url, ValidateSellerDTO.class);
        } catch (Exception e) {
            log.error("Error validating seller with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error validating seller", e);
        }
    }

    private Shop mapDtoToShop(CreateShopDTO dto, UUID ownerId) {
        Shop shop = new Shop();
        shop.setId(UUID.randomUUID());
        shop.setShopName(dto.getShopName());
        shop.setDescription(dto.getDescription());
        shop.setLocation(dto.getLocation());
        shop.setLocationDescription(dto.getLocationDescription());
        shop.setContactInfo(dto.getContactInfo());
        // Add owner to the shop
        ShopRole owner = new ShopRole(UUID.randomUUID(), shop, ownerId, Role.OWNER);
        shop.setOwners(Set.of(owner));
        return shop;
    }
}

