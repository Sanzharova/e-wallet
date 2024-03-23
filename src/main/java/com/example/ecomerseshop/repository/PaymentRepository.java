package com.example.ecomerseshop.repository;

import com.example.ecomerseshop.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    @Query(value = "SELECT * FROM wallet.payments WHERE wallet.payments.status = ?1", nativeQuery = true)
    List<PaymentEntity> getByStatus(@Param("status") String status);
}
