package com.example.ecomerseshop.config;

import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.utils.ValidateGoogleAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.core.convert.converter.Converter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    private final UserRepository userRepository;

    private final ValidateGoogleAuthToken validateGoogleAuthToken;

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
                          LogoutHandler logoutHandler,
                          UserRepository userRepository,
                          ValidateGoogleAuthToken validateGoogleAuthToken) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
        this.userRepository = userRepository;
        this.validateGoogleAuthToken = validateGoogleAuthToken;
    }


    @Bean
    Converter<Jwt, AbstractAuthenticationToken> customJwtAuthenticationConverter() {
        return new JwtAuthenticationOAuth2Converter(userRepository, validateGoogleAuthToken);
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
                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(customJwtAuthenticationConverter())
                        )

                );

        return http.build();
    }
}
