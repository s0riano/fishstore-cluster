package com.fishstore.authentication.user;

import com.alibou.security.token.Token;
import com.fishstore.authentication.enums.ShopRole;
import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    private UUID id;
    private String email;
    private String password;

    @Transient
    private Map<UUID, ShopRole> shopRolesMap; // Storing the roles per store

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the map values (roles) to GrantedAuthority objects
        return shopRolesMap.values().stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
