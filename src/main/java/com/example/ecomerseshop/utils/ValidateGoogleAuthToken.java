package com.example.ecomerseshop.utils;

import com.example.ecomerseshop.entity.UserEntity;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class ValidateGoogleAuthToken {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateGoogleAuthToken.class);

    @Value("google_client_id")
    private String CLIENT_ID;

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new GsonFactory();

    public Optional<UserEntity> verifyGoogleAuthToken(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String pictureUrl = (String) payload.get("picture");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                UserEntity user = new UserEntity(
                        (givenName == null) ? " " : givenName.substring(0, Math.min(givenName.length(), 25)),
                        (familyName == null) ? " " : familyName.substring(0, Math.min(familyName.length(), 30)),
                        email.substring(0, Math.min(email.length(), 40)),
                        pictureUrl
                );
                return Optional.of(user);
            } else {
                LOG.info("Invalid ID token.");
            }
        } catch (Exception e) {
            LOG.info("Exception in verifyGoogleAuthToken: ", e);
        }
        return Optional.empty();
    }
}