package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.*;
import com.example.ecomerseshop.dao.PaymentDataAccess;
import com.example.ecomerseshop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;
    private final PaymentDataAccess paymentDataAccess;

    public ResponseEntity<String> checkPayment(PaymentCheck paymentCheck) {
        String result = paymentService.checkPayment(paymentCheck);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> createPayment(Payment payment, int id) {
        String result = paymentService.createPayment(payment, id);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> confirmPayment(int id) {
        String result = paymentService.confirmPayment(id);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> rollbackPayment(int id,
                                                  RollbackPaymentWalletRequest walletRequest) {
        String result = paymentService.rollbackPayment(id, walletRequest);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> getByStatusPayment(String status) {
        String result = paymentDataAccess.getByStatusPayment(status);
        return ResponseEntity.ok(result);
    }
}

