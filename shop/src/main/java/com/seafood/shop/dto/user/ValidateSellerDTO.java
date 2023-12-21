package com.seafood.shop.dto.user;


import com.seafood.shop.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateSellerDTO {
    private UUID userID;
    private UserStatus status;
}
