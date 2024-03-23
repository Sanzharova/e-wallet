package com.example.ecomerseshop.payment;

import com.example.ecomerseshop.dao.FavourDataAccess;
import com.example.ecomerseshop.dao.WalletDataAccess;
import com.example.ecomerseshop.dto.*;
import com.example.ecomerseshop.repository.PaymentRepository;
import com.example.ecomerseshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private FavourDataAccess favourDataAccess;

    @Mock
    private WalletDataAccess walletDataAccess;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCheckPayment_Success() {
        PaymentCheck paymentCheck = new PaymentCheck(new BigDecimal(1000), "bankBookCheck");
        PaymentEntity paymentEntity = new PaymentEntity();
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        String result = paymentService.checkPayment(paymentCheck);

        assertEquals("Проверена услуга", result);
        verify(paymentRepository, times(1)).save(any());
    }

    @Test
    void testCheckPayment_Failure() {
        PaymentCheck paymentCheck = new PaymentCheck(new BigDecimal(300), null);

        String result = paymentService.checkPayment(paymentCheck);

        assertEquals("Ошибка, у вас проблема с услугой или банковским счетом!", result);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void testCreatePayment_Success() {
        Payment paymentRequest = new Payment();
        paymentRequest.setFavourId(1);
        paymentRequest.setWalletId(1);
        paymentRequest.setPrice(new BigDecimal(100));
        paymentRequest.setBankBookCheck("bankBookCheck");

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        paymentEntity.setIsChecked(true);

        Favour favour = new Favour();
        favour.setId(1);

        Wallet wallet = new Wallet();
        wallet.setId(1);
        wallet.setAmount(new BigDecimal(200));

        when(paymentRepository.findById(anyInt())).thenReturn(Optional.of(paymentEntity));
        when(favourDataAccess.getById(anyInt())).thenReturn(Optional.of(favour));
        when(walletDataAccess.getById(anyInt())).thenReturn(Optional.of(wallet));
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        String result = paymentService.createPayment(paymentRequest, 1);

        assertEquals("Платеж был создан", result);
        verify(paymentRepository, times(1)).save(any());
    }

    // Additional test cases can be added for different scenarios
}

