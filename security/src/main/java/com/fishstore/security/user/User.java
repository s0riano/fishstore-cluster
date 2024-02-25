package com.fishstore.security.user;

import com.fishstore.security.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

  @Column(name = "shop_id")
  private UUID shopId;

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


  @Enumerated(EnumType.STRING)
  @Column(
          name = "role"
  )
  private Role role;

  /*@Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }*/

  public User(String email, String password, UUID shopId, Role role) {
    this.email = email;
    this.password = password;
    this.shopId = shopId;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
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
