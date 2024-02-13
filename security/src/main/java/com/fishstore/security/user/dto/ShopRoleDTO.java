package com.fishstore.security.user.dto;


import com.fishstore.security.user.Role;
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
