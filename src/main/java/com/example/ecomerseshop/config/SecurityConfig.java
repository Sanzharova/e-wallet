package com.example.ecomerseshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    private static final String[] AUTH_WHITELIST = {
            "/auth/logout",
            "/wallets",
            "/favours",
            "/users",
            "/payments"
    };


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          AuthenticationProvider authenticationProvider,
                          LogoutHandler logoutHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(AUTH_WHITELIST)
                                .authenticated()
                                .anyRequest()
                                .permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout
                                .logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                );

        return http.build();
    }
}
