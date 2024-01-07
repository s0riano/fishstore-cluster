package com.fishstore.authentication.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class VerifySellerComponent {
    private final RestTemplate restTemplate;
    private final String sellerServiceApiUrl;

    public VerifySellerComponent(RestTemplate restTemplate,
                                 String sellerServiceApiUrl) {
        this.restTemplate = restTemplate;
        this.sellerServiceApiUrl = sellerServiceApiUrl;
    }


}
