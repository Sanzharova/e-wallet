package com.example.ecomerseshop.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {
    String phoneNumber;
    String firstName;
    String lastName;
    String middleName;
    String password;

}
