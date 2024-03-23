package com.example.ecomerseshop.repository;

import com.example.ecomerseshop.entity.FavourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FavourRepository extends JpaRepository<FavourEntity, Integer>, JpaSpecificationExecutor<FavourEntity> {
}
