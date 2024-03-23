package com.example.ecomerseshop.repository;

import com.example.ecomerseshop.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WalletRepository extends JpaRepository<WalletEntity, Integer>, JpaSpecificationExecutor<WalletEntity> {
}
