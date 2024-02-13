package com.fishstore.authentication.config;

import com.fishstore.authentication.components.CustomAuthenticationEntryPoint;
import com.fishstore.authentication.components.JwtTokenProvider;
import com.fishstore.authentication.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          AuthenticationConfiguration authenticationConfiguration,
                          JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtConfigurer jwtConfigurer() {
        return new JwtConfigurer(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(rmr -> rmr
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/api/admin/**").hasRole(Role.OWNER.name())
                        .requestMatchers("/api/staff/**").hasAnyRole(Role.STAFF.name(), Role.OWNER.name())
                        .requestMatchers("/api/**").authenticated()
                )
                .httpBasic(httpbc -> httpbc
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .sessionManagement(smc -> smc
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable);

        // Apply JwtConfigurer which adds the JwtTokenFilter
        http.apply(new JwtConfigurer(jwtTokenProvider));
        //http.apply(jwtConfigurer());
        //.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // Configure AuthenticationManager
        http.authenticationManager(authenticationManager());

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

