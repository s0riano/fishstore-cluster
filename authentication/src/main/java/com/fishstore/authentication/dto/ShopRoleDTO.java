package com.fishstore.authentication.dto;


import com.fishstore.authentication.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopRoleDTO {
    private UUID shopId;
    private Role role;
}
