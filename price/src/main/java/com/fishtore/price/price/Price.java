package com.fishtore.price.price;

import com.fishtore.price.dto.PriceAdjusted;
import com.fishtore.price.dto.PriceEntryDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "price")
public class Price {
    @Id
    @NotNull
    private String id; //this is the shopId // is uuid, might modify
    @NotNull
    private List<PriceEntryDTO> prices;
    PriceAdjusted info;
}
