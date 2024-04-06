package com.fastcampus.apigateway.global.config;

import com.fastcampus.apigateway.global.security.JwtReactiveAuthenticationManager;
import com.fastcampus.apigateway.global.security.JwtReactiveSecurityContextRepository;
import com.fastcampus.apigateway.global.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ReactiveSecurityConfig {
    @Bean
    public JwtReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        return new JwtReactiveAuthenticationManager(userDetailsService, passwordEncoder);
    }

    @Bean
    public JwtReactiveSecurityContextRepository securityContextRepository(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        return new JwtReactiveSecurityContextRepository(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                .pathMatchers("/login").permitAll()
                                .pathMatchers("/actuator/**").permitAll()
                                .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager(userDetailsService, passwordEncoder))
                .securityContextRepository(securityContextRepository(jwtUtil, userDetailsService));
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("fast").password(passwordEncoder().encode("campus")).roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user, admin);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
