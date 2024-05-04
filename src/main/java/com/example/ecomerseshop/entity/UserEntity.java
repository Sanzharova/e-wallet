package com.example.ecomerseshop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "middle_name")
    String middleName;

    @Column(name = "credentials_expiry_date")
    LocalDateTime credentialsExpiryDate;

    @Column(name = "is_account_non_expired")
    Boolean isAccountExpired;

    @Column(name = "is_account_non_locked")
    Boolean isAccountLocked;

    @Column(name = "is_active", nullable = false)
    Boolean isActive;

    @Column(name = "is_enabled")
    Boolean isEnabled;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}
