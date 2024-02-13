package com.fishstore.security.user;

import com.fishstore.security.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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


  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Transient
  private Map<UUID, Role> shopRolesMap;
  /*@Enumerated(EnumType.STRING)
  private Role role;*/

  /*@Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }*/

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
