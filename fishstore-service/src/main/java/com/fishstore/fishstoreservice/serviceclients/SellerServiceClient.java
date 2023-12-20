package com.fishstore.fishstoreservice.serviceclients;


import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class SellerServiceClient {

    private final RestTemplate restTemplate;

    private final String sellerServiceUrl;

    public SellerServiceClient(final RestTemplateBuilder builder){
                               //@Value("${service.seller.host}") final String sellerServiceUrl){
     restTemplate = builder.build();
     this.sellerServiceUrl = "http://localhost:8080"; // Brukes for å tildele ip til retrieveSellers under
    }


    public String retrieveSellers(){
        try{
            ResponseEntity<String> r = restTemplate.getForEntity(
                    sellerServiceUrl + "/api/sellers",
                    String.class
            );
            log.info("Retrieved sellers successfully");

            return r.getBody();
        }
        catch(Exception e){
            log.error("There is an error: retrieving sellers: ", e);
            return null;
        }
    }

}
