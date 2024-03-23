package com.example.ecomerseshop.service;

import com.example.ecomerseshop.dao.FavourDataAccess;
import com.example.ecomerseshop.dao.WalletDataAccess;
import com.example.ecomerseshop.dto.*;
import com.example.ecomerseshop.entity.PaymentEntity;
import com.example.ecomerseshop.entity.PaymentStatus;
import com.example.ecomerseshop.mapper.FavourMapper;
import com.example.ecomerseshop.mapper.WalletMapper;
import com.example.ecomerseshop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final FavourDataAccess favourDataAccess;
    private final WalletDataAccess walletDataAccess;
    private final FavourMapper favourMapper;
    private final WalletMapper walletMapper;


    public String checkPayment(PaymentCheck paymentCheckRequest) {

        var minValue = new BigDecimal(500);
        var maxValue = new BigDecimal(25000);

        if(paymentCheckRequest.getAmountOfFavour().compareTo(minValue) < 0
                && paymentCheckRequest.getAmountOfFavour().compareTo(maxValue) < 0
                && paymentCheckRequest.getBankBookCheck() != null) {

            PaymentEntity payment = new PaymentEntity();

            payment.setIsChecked(Boolean.TRUE);
            payment.setStatus(PaymentStatus.STATUS_PROCESS);

            paymentRepository.save(payment);

            return "Проверена услуга";
        }
        else return "Ошибка, у вас проблема с услугой или банковским счетом!";
    }

    public String createPayment(Payment paymentRequest, int id) {
        PaymentEntity payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Не был найден платеж с таким идентификатором!"));

        Favour favour = favourDataAccess
                .getById(paymentRequest.getFavourId())
                .orElseThrow(() -> new RuntimeException("Не был найдена услуга с таким идентификатором!"));

        Wallet wallet = walletDataAccess
                .getById(paymentRequest.getWalletId())
                .orElseThrow(() -> new RuntimeException("Не был найден кошелек с таким идентификатором!"));

        if(wallet.getAmount().compareTo(paymentRequest.getPrice()) < 0) {

            return "У вас недостаточно средств!";
        }

        if(payment.getIsChecked().equals(Boolean.TRUE)) {

            payment.setStatus(PaymentStatus.STATUS_CREATED);
            payment.setCreated_at(Timestamp.from(Instant.now()));
            payment.setFinished(Boolean.FALSE);
            payment.setFavour(favourMapper.toEntity(favour));
            payment.setSumOfFavour(paymentRequest.getPrice());
            payment.setBankBookCheck(paymentRequest.getBankBookCheck());
            payment.setWallet(walletMapper.toEntity(wallet));
            payment.getWallet().setAmount(wallet.getAmount().subtract(payment.getSumOfFavour()));

            paymentRepository.save(payment);
            return "Платеж был создан";
        } else return "Ошибка, у вас проблема с услугой или банковским счетом!";
    }

    public String confirmPayment(int id) {

        PaymentEntity payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Не был найден платеж с таким идентификатором!"));

        payment.setFinished(Boolean.TRUE);
        if(payment.getFinished().equals(Boolean.TRUE)) payment.setStatus(PaymentStatus.STATUS_SUCCESS);
        payment.setUpdated_at(Timestamp.from(Instant.now()));

        paymentRepository.save(payment);

        return "Статус платежа: " + payment.getStatus();
    }

    public String rollbackPayment(int id, RollbackPaymentWalletRequest walletRequest) {
        PaymentEntity payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Не был найден платеж с таким идентификатором!"));

        Wallet wallet = walletDataAccess
                .getById(walletRequest.getWalletId())
                .orElseThrow(() -> new RuntimeException("Не был найден кошелек с таким идентификатором!"));

        if(payment.getStatus().equals(PaymentStatus.STATUS_SUCCESS)) {

            long Milli = Math.abs(payment.getUpdated_at().getTime() - new Date().getTime());

            if(Milli < 1080000) {
                payment.setStatus(PaymentStatus.STATUS_ROLLBACK);
                payment.getWallet().setAmount(wallet.getAmount().add(payment.getSumOfFavour()));
            } else return "3 дня уже прошли";
        } else if(payment.getStatus().equals(PaymentStatus.STATUS_CREATED)) {
            payment.setStatus(PaymentStatus.STATUS_ROLLBACK);
            payment.getWallet().setAmount(wallet.getAmount().add(payment.getSumOfFavour()));
        } else return "Извините, у вас нету платежа или ошибка с транзакцией";

        payment.setUpdated_at(Timestamp.from(Instant.now()));

        paymentRepository.save(payment);

        return "Платеж откатен";
    }
}
