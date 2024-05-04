package com.example.ecomerseshop.repository;

import com.example.ecomerseshop.entity.RefreshTokenEntity;
import com.example.ecomerseshop.entity.UserDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, Integer> {
    Optional<UserDeviceEntity> findById(int id);
    Optional<UserDeviceEntity> findByRefreshToken(RefreshTokenEntity refreshToken);
    Optional<UserDeviceEntity> findByUserIdAndDeviceId(Integer userId, String userDeviceId);
}