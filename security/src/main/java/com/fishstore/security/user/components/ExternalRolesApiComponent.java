package com.fishstore.security.user.components;

import com.fishstore.security.user.dto.ShopRoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class ExternalRolesApiComponent {

    private final RestTemplate restTemplate;
    private final String shopApiUrl;

    @Autowired
    public ExternalRolesApiComponent(RestTemplate restTemplate, @Value("${shop.api.url}") String shopApiUrl) {
        this.restTemplate = restTemplate;
        this.shopApiUrl = shopApiUrl;
    }

    public Set<ShopRoleDTO> getListOfRoles(UUID userId) {
        String url = String.format("%s/api/roles/%s", shopApiUrl, userId);

        try {
            ResponseEntity<Set<ShopRoleDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Set<ShopRoleDTO>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Error fetching roles for user ID {}: {}", userId, e.getMessage(), e);
            return Set.of();
        }
    }

}
