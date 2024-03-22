package com.example.ecomerseshop.repository;

import com.example.ecomerseshop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
