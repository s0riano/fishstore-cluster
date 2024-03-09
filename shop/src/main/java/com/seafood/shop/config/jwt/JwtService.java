package com.seafood.shop.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public List<String> extractAuthorities(String token) {
    return extractClaim(token, claims -> claims.get("roles", List.class));
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public boolean isTokenValid(String token) {
    try {
      return !isTokenExpired(token);
    } catch (ExpiredJwtException e) {
      log.info("Token expired: {}", token); // Token is expired
      return false;
    } catch (SignatureException e) {
      log.warn("Invalid JWT signature: {}", token); // Signature mismatch
      return false;
    } catch (Exception e) {
      log.error("Token validation error: {}", e.getMessage()); // Other errors
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Claims extractAllClaims(String token) throws SignatureException {
    try {
      return Jwts
              .parserBuilder()
              .setSigningKey(getSignInKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (SignatureException e) {
      throw e; // rethrow the exception
    } catch (Exception e) {
      log.error("Error in parsing token: {}", e.getMessage());
      throw e; // or you can handle it as per your application's need
    }
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractPrincipal(String jwt) {
    Jws<Claims> jwsClaims = Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(jwt);
    return jwsClaims.getBody().getSubject();
  }
}
