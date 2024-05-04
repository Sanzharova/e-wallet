package com.example.ecomerseshop.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "auth", name = "user_device")
public class UserDeviceEntity extends DateAudit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    UserEntity user;

    @Column(name = "device_type")
    @Enumerated(value = EnumType.STRING)
    DeviceType deviceType;

    @Column(name = "device_id")
    String deviceId;

    @OneToOne(optional = false, mappedBy = "userDevice")
    RefreshTokenEntity refreshToken;

    @Column(name = "is_refresh_active")
    Boolean isRefreshActive;
}