package com.fishstore.security.config;

import com.fishstore.security.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;


    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //filterChain.doFilter(request, response);
      return;
    }

    log.info("Authorization Header: " + authHeader);

    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);

    log.info("Authorization Header transformed to mail: " + userEmail);

    if (userEmail != null /*&& SecurityContextHolder.getContext().getAuthentication() == null*/) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      var isTokenValidTest = tokenRepository.findByToken(jwt);
      log.info("isTokenValid test: " + isTokenValidTest);

      var isTokenValid = tokenRepository.findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);

      // ------------ error takes place somewhere before here ------------

      if (!isTokenValid) {
        log.info("Token not found or invalid.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //filterChain.doFilter(request, response);
        return;
      }

      log.info("Token is Valid");
      log.info("User Details: " + userDetails);
      log.info("User Authorities: " + userDetails.getAuthorities());
      log.info("Authentication Token: " + SecurityContextHolder.getContext().getAuthentication());

      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
