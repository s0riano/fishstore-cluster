package com.seafood.seller.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class FetchBuyerApiComponent {

    private final RestTemplate restTemplate;
    private final String priceApiUrl;

    @Autowired
    public FetchBuyerApiComponent(RestTemplate restTemplate, @Value("${external.buyer.api.url}") String priceApiUrl) {
        this.restTemplate = restTemplate;
        this.priceApiUrl = priceApiUrl;
    }
}
