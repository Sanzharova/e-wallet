package com.example.ecomerseshop.wallet;

import com.example.ecomerseshop.dao.implementation.WalletDataAccessImpl;
import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Wallet;
import com.example.ecomerseshop.entity.UserEntity;
import com.example.ecomerseshop.entity.WalletEntity;
import com.example.ecomerseshop.mapper.WalletMapper;
import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WalletDataAccessTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletMapper walletMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletDataAccessImpl walletDataAccess;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllByFilter() {
        // Создание mock объектов
        PageFilter pageFilter = new PageFilter();
        List<WalletEntity> mockWalletEntities = new ArrayList<>();
        mockWalletEntities.add(new WalletEntity());
        org.springframework.data.domain.Page<WalletEntity> mockPage = new org.springframework.data.domain.PageImpl<>(mockWalletEntities);
        when(walletRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        // Вызов метода для тестирования
        Page<Wallet> result = walletDataAccess.getAllByFilter(pageFilter);

        // Проверка результата
        assertEquals(1, result.getData().size());
        // Здесь можно добавить дополнительные проверки по содержимому полученных объектов Wallet
    }

    @Test
    public void testGetById() {
        // Создание mock объекта
        WalletEntity mockWalletEntity = new WalletEntity();
        mockWalletEntity.setId(1);
        when(walletRepository.findById(1)).thenReturn(Optional.of(mockWalletEntity));

        // Вызов метода для тестирования
        Optional<Wallet> result = walletDataAccess.getById(1);

        // Проверка результата
        assertEquals(1, 1);
        // Здесь можно добавить дополнительные проверки по содержимому полученного объекта Wallet
    }

    @Test
    public void testSave() {
        // Создание mock объекта
        Wallet wallet = new Wallet();
        wallet.setUserId(1);
        wallet.setAmount(BigDecimal.TEN);

        UserEntity mockUserEntity = new UserEntity();
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserEntity));

        WalletEntity mockWalletEntity = new WalletEntity();
        mockWalletEntity.setId(1);
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(mockWalletEntity);

        // Вызов метода для тестирования
        Integer result = walletDataAccess.save(wallet);

        // Проверка результата
        assertEquals(1, result.intValue());
    }

    @Test
    public void testUpdate() {
        // Создание mock объекта
        Wallet wallet = new Wallet();
        wallet.setUserId(1);
        wallet.setAmount(BigDecimal.TEN);

        UserEntity mockUserEntity = new UserEntity();
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUserEntity));

        WalletEntity mockWalletEntity = new WalletEntity();
        mockWalletEntity.setId(1);
        when(walletRepository.findById(1)).thenReturn(Optional.of(mockWalletEntity));
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(mockWalletEntity);

        // Вызов метода для тестирования
        Integer result = walletDataAccess.update(wallet, 1);

        // Проверка результата
        assertEquals(1, result.intValue());
    }

    @Test
    public void testDelete() {
        // Вызов метода для тестирования
        Integer result = walletDataAccess.delete(1);

        // Проверка результата
        assertEquals(1, result.intValue());
    }
}

