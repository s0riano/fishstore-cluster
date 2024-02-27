package com.seafood.shop.config;

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

    final String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      final String jwt = authHeader.substring(7);
      if (jwtService.isTokenValid(jwt)) {
        setSecurityContext(jwt, request);
      } else {
        setUnauthorizedResponse(response);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private void setSecurityContext(String jwt, HttpServletRequest request) {
    // Extract roles or authorities from JWT
    List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(jwt).stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    // Create an authentication token
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            jwt, null, authorities);
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  private void setUnauthorizedResponse(HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
