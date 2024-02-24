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

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

  @Id
  @Column(
          name = "id",
          columnDefinition = "uuid",
          updatable = false,
          nullable = false
  )
  private UUID id;

  @Column(
          name = "email"
  )
  private String email;

  @Column(
          name = "password"
  )
  private String password;


  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Token> tokens;

  @Transient
  private Map<UUID, Role> shopRolesMap;
  /*@Enumerated(EnumType.STRING)
  private Role role;*/

  /*@Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }*/

  public User(String email, String password, Map<UUID, Role> shopRolesMap) {
    this.email = email;
    this.password = password;
    this.shopRolesMap = shopRolesMap != null ? new HashMap<>(shopRolesMap) : new HashMap<>();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (shopRolesMap == null) {
      return Collections.emptySet();
    }
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

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", tokens=" + (tokens != null ? tokens.size() : "null") +
            '}';
  }
}
