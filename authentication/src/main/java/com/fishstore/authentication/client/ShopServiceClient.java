package com.fishstore.authentication.client;

import com.fishstore.authentication.enums.ShopRole;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class ShopServiceClient {
    private final RestTemplate restTemplate;
    private final String shopServiceApiUrl;

    public ShopServiceClient(
            RestTemplate restTemplate,
            @Value("${external.shop.api.url}") String shopServiceApiUrl
    ) {
        this.restTemplate = restTemplate;
        this.shopServiceApiUrl = shopServiceApiUrl;
    }

    public ShopRole[] getRolesForUser(UUID userId) {
        String url = String.format("%s/shop/users/%s/roles", shopServiceApiUrl, userId.toString());
        try {
            return restTemplate.getForObject(url, ShopRole[].class);
        } catch (Exception e) {
            // Handle exception, possibly logging the error or rethrowing as a custom exception
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching roles from Shop Service", e);
        }
    }
}
