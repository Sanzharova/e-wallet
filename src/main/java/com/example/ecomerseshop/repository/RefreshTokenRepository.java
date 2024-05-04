package com.example.ecomerseshop.repository;

import com.example.ecomerseshop.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {
    Optional<RefreshTokenEntity> findById(int id);
    RefreshTokenEntity findByToken(String token);
}