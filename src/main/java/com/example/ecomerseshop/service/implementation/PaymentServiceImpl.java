package com.example.ecomerseshop.service.implementation;

import com.example.ecomerseshop.dto.*;
import com.example.ecomerseshop.entity.PaymentEntity;
import com.example.ecomerseshop.entity.PaymentStatus;
import com.example.ecomerseshop.mapper.FavourMapper;
import com.example.ecomerseshop.mapper.WalletMapper;
import com.example.ecomerseshop.repository.PaymentRepository;
import com.example.ecomerseshop.service.FavourService;
import com.example.ecomerseshop.service.PaymentService;
import com.example.ecomerseshop.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final FavourService favourService;
    private final WalletService walletService;
    private final FavourMapper favourMapper;
    private final WalletMapper walletMapper;


    @Override
    public String checkPayment(PaymentCheck paymentCheckRequest) {

        if(paymentCheckRequest.getAmountOfFavour() > 500 && paymentCheckRequest.getAmountOfFavour() < 25000 &&
                paymentCheckRequest.getAccountCheck() != null) {

            PaymentEntity payment = new PaymentEntity();

            payment.setIsChecked(Boolean.TRUE);
            payment.setStatus(PaymentStatus.STATUS_PROCESS);

            paymentRepository.save(payment);

            return "Order is checked!";
        }
        else return "Error, you have problem with sumOfFavour or AccountCheck!";
    }

    @Override
    public String createPayment(Payment paymentRequest, int id) {
        PaymentEntity payment = paymentRepository
                                    .findById(id)
                                    .orElseThrow(() -> new RuntimeException("Не был найден платеж с таким идентификатором!"));

        Favour favour = favourService
                .getById(paymentRequest.getFavourId())
                .orElseThrow(() -> new RuntimeException("Не был найдена услуга с таким идентификатором!"));

        Wallet wallet = walletService
                .getById(paymentRequest.getWalletId())
                .orElseThrow(() -> new RuntimeException("Не был найден кошелек с таким идентификатором!"));

        if(wallet.getAmount().compareTo(paymentRequest.getPrice()) < 0) {

            return "You don't have money! Sorry!";
        }

        if(payment.getIsChecked().equals(Boolean.TRUE)) {

            payment.setStatus(PaymentStatus.STATUS_CREATED);
            payment.setCreated_at(Timestamp.from(Instant.now()));
            payment.setFinished(Boolean.FALSE);
            payment.setFavour(favourMapper.toEntity(favour));
            payment.setSumOfFavour(paymentRequest.getPrice());
            payment.setAccountCheck(paymentRequest.getAccountCheck());
            payment.getWallet().setAmount(wallet.getAmount().subtract(payment.getSumOfFavour()));

            paymentRepository.save(payment);
            return "Payment Created";
        } else return "You have some with problem with AccountCheck or SumOfFavour";
    }

    @Override
    public String confirmPayment(int id) {

        PaymentEntity payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Не был найден платеж с таким идентификатором!"));

        payment.setFinished(Boolean.TRUE);
        if(payment.getFinished().equals(Boolean.TRUE)) payment.setStatus(PaymentStatus.STATUS_SUCCESS);
        payment.setUpdated_at(Timestamp.from(Instant.now()));

        paymentRepository.save(payment);

        return "Your status in payment: " + payment.getStatus();
    }

    @Override
    public String rollbackPayment(int id, RollbackPaymentWalletRequest walletRequest) {
        PaymentEntity payment = paymentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Не был найден платеж с таким идентификатором!"));

        Wallet wallet = walletService
                .getById(walletRequest.getWalletId())
                .orElseThrow(() -> new RuntimeException("Не был найден кошелек с таким идентификатором!"));

        if(payment.getStatus().equals(PaymentStatus.STATUS_SUCCESS)) {

            long Milli = Math.abs(payment.getUpdated_at().getTime() - new Date().getTime());

            if(Milli < 1080000) {
                payment.setStatus(PaymentStatus.STATUS_ROLLBACK);
                payment.getWallet().setAmount(wallet.getAmount().add(payment.getSumOfFavour()));
            } else return "3 days gone";
        } else if(payment.getStatus().equals(PaymentStatus.STATUS_CREATED)) {
            payment.setStatus(PaymentStatus.STATUS_ROLLBACK);
            payment.getWallet().setAmount(wallet.getAmount().add(payment.getSumOfFavour()));
        } else return "You dont have payment or your status fail";

        payment.setUpdated_at(Timestamp.from(Instant.now()));

        paymentRepository.save(payment);

        return "Rollback payment";
    }

    @Override
    public String getByStatusPayment(String status) {
        List<PaymentEntity> payment = paymentRepository.getByStatus(status);
        return "Payment with status: " + payment;
    }
}
