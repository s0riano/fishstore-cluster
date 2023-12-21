package com.seafood.inventory.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
public class VerifyShopComponent {
    private final RestTemplate restTemplate;
    private final String shopServiceApiUrl;

    public VerifyShopComponent(RestTemplate restTemplate, @Value("${external.shop.api.url}") String shopServiceApiUrl) {
        this.restTemplate = restTemplate;
        this.shopServiceApiUrl = shopServiceApiUrl;
    }

    public Boolean verifyIfShopExist(UUID shopId) {
        String url = String.format("%s/shops/active/all/ids", shopServiceApiUrl);
        try {
            UUID[] activeShopIds = restTemplate.getForObject(url, UUID[].class);
            //log.info("url: {}", url);
            log.info("activeShopIds: {}", (Object[]) activeShopIds);
            if (activeShopIds != null) {
                return Arrays.asList(activeShopIds).contains(shopId);
            } else {
                //log.error("Error in checking if shop exist");
                log.error("Check if shop is running xd");
            }
        } catch (Exception e) {
            log.error("Error verifying shop existence", e);
        }
        return false;
    }

}
