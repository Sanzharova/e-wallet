package com.example.ecomerseshop.service;

import com.example.ecomerseshop.dto.Payment;
import com.example.ecomerseshop.dto.PaymentCheck;
import com.example.ecomerseshop.dto.RollbackPaymentWalletRequest;

public interface PaymentService {
    String checkPayment(PaymentCheck paymentCheckRequest);

    String createPayment(Payment paymentRequest, int id);

    String confirmPayment(int id, RollbackPaymentWalletRequest walletRequest);

    String rollbackPayment(int id, RollbackPaymentWalletRequest walletRequest);

    String getByStatusPayment(String status);
}
