package dto.preorder;

import dto.TransactionItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreOrderDTO {
    private UUID shopId;
    private UUID inventoryId;
    private UUID buyerId;
    private List<TransactionItemDTO> items;
}
