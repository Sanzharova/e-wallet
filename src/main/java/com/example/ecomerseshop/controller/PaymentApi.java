package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Payment;
import com.example.ecomerseshop.dto.PaymentCheck;
import com.example.ecomerseshop.dto.RollbackPaymentWalletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payments")
public interface PaymentApi {

    @PostMapping("/check")
    ResponseEntity<String> checkPayment(@Valid @RequestBody PaymentCheck paymentCheck);

    @PostMapping("/create/{id}")
    ResponseEntity<String> createPayment(@Valid @RequestBody Payment payment, @PathVariable int id);

    @PostMapping("/confirm/{id}")
    ResponseEntity<String> confirmPayment(@PathVariable int id);

    @PostMapping("/rollback/{id}")
    ResponseEntity<String> rollbackPayment(@PathVariable int id,
                                           @Valid @RequestBody RollbackPaymentWalletRequest walletRequest);

    @GetMapping("/status/{status}")
    ResponseEntity<String> getByStatusPayment(@PathVariable String status);
}
