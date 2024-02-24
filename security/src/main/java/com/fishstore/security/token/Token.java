package com.fishstore.security.token;

import com.fishstore.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(unique = true)
  private String token;

  @Column(name = "token_type")
  @Enumerated(EnumType.STRING)
  private TokenType tokenType = TokenType.BEARER; //Removed "= TokenType.BEARER;"

  private boolean revoked;
  private boolean expired;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Override
  public String toString() {
    return "Token{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", tokenType=" + tokenType +
            ", revoked=" + revoked +
            ", expired=" + expired +
            ", user=" + (user != null ? user.getId() : "null") +
            '}';
  }
}
