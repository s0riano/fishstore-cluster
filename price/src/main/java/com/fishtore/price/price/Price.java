package com.fishtore.price.price;

import com.fishtore.price.dto.PriceEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "price")
public class Price {
    private Long sellerId;
    private List<PriceEntryDTO> prices;
}
