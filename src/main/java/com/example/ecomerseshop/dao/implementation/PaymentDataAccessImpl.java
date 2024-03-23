package com.example.ecomerseshop.dao.implementation;

import com.example.ecomerseshop.entity.PaymentEntity;
import com.example.ecomerseshop.dao.PaymentDataAccess;
import com.example.ecomerseshop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PaymentDataAccessImpl implements PaymentDataAccess {

    private final PaymentRepository paymentRepository;

    @Override
    public String getByStatusPayment(String status) {
        List<PaymentEntity> payment = paymentRepository.getByStatus(status);
        return "Payment with status: " + payment;
    }
}
