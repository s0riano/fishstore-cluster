package com.fishstore.fishstoreservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class StoreAttemptDTO {

    @NotNull //sjekk om alt er jakarta vs hibernate ? evt sp;r
    Long sellerId;
    @NotBlank
    String storeName;
}
