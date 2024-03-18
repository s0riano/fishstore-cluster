package com.seafood.inventory.config.jwt;

import com.seafood.shop.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    try {
      String jwt = extractToken(request);
      if (jwt != null && jwtService.isTokenValid(jwt)) {
        CustomUserDetails userDetails = parseJwtToUserDetails(jwt); //test
        setSecurityContext(jwt, request);
      } else {
        setUnauthorizedResponse(response); // Token is present but not valid
      }
      filterChain.doFilter(request, response);
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      log.info("Expired token nigga");
      handleExpiredJwt(request, response); // Handle expired JWT specifically
    }

  }

  private CustomUserDetails parseJwtToUserDetails(String jwt) { //test
    // Logic to parse JWT and create CustomUserDetails
    Claims claims = jwtService.extractAllClaims(jwt);
    UUID storeId = UUID.fromString(claims.get("store_id", String.class));
    String username = claims.getSubject();

    // Assume roles are part of the JWT and map them to GrantedAuthorities
    List<String> roles = jwtService.extractAuthorities(jwt); // Extract roles from JWT
    List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new CustomUserDetails(storeId, username, authorities);
  }

  private void setSecurityContext(String jwt, HttpServletRequest request) {
    Claims claims = jwtService.extractAllClaims(jwt);
    UUID storeId = UUID.fromString(claims.get("store_id", String.class));
    List<String> roles = claims.get("roles", List.class);

    CustomUserDetails userDetails = new CustomUserDetails(storeId, claims.getSubject(),
            roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  private void setUnauthorizedResponse(HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }

  private String extractToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7); // Remove 'Bearer ' prefix
    }
    return null;
  }

  private void handleExpiredJwt(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // You can redirect to a login page, issue a new token if you have refresh token logic,
    // or inform the client that the token has expired and action is needed.
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is expired");
  }
}
