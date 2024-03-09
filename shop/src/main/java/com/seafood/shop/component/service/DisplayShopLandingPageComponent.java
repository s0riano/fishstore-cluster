package com.seafood.shop.component.service;

import com.seafood.shop.dto.shopDTOs.ShopLandingPageDTO;
import com.seafood.shop.shop.Shop;
import com.seafood.shop.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisplayShopLandingPageComponent {

    private final ShopRepository shopRepository;

    @Autowired
    public DisplayShopLandingPageComponent(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<ShopLandingPageDTO> getAllActiveShops() {
        List<Shop> shops = shopRepository.findAll(); // Custom query method
        //for each, search their inventory and what they are selling?
        // can be drop for now, haster ikke
        return shops.stream()
                .map(this::mapToShopLandingPageDTO)
                .collect(Collectors.toList());
    }

    private ShopLandingPageDTO mapToShopLandingPageDTO(Shop shop) {
        ShopLandingPageDTO dto = new ShopLandingPageDTO();
        dto.setId(shop.getId());
        dto.setName(shop.getShopName());

        //call inventory or price service to fetch what the serller is selling, then set it
        return dto;
    }


}
