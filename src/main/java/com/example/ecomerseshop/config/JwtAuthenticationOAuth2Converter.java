package com.example.ecomerseshop.config;

import com.example.ecomerseshop.entity.UserEntity;
import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.utils.ValidateGoogleAuthToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtAuthenticationOAuth2Converter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;
    private final ValidateGoogleAuthToken validateGoogleAuthToken;

    public JwtAuthenticationOAuth2Converter(UserRepository userRepository, ValidateGoogleAuthToken validateGoogleAuthToken) {
        this.userRepository = userRepository;
        this.validateGoogleAuthToken = validateGoogleAuthToken;
    }

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        UserEntity user = validateGoogleAuthToken.verifyGoogleAuthToken(jwt.getTokenValue())
                .orElseThrow(() -> new RuntimeException("Failed to validate JWT."));
        UserEntity isAlreadyEnabledUser = userRepository.findByUsername(user.getUsername());

        List<GrantedAuthority> authorities = List.of();

        if (isAlreadyEnabledUser != null && isAlreadyEnabledUser.getIsEnabled()) {
            authorities = isAlreadyEnabledUser.getAuthorities().stream().map(role ->
                    new SimpleGrantedAuthority(role.getAuthority())
            ).collect(Collectors.toList());
        }

        String principalClaimName = JwtClaimNames.SUB;
        String principalClaimValue = jwt.getClaimAsString(principalClaimName);
        return new JwtAuthenticationToken(jwt, authorities, principalClaimValue);
    }

}