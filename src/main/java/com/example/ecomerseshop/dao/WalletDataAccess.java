package com.example.ecomerseshop.dao;

import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.Wallet;

import java.util.Optional;

public interface WalletDataAccess {
    Page<Wallet> getAllByFilter(PageFilter walletFilter);
    Optional<Wallet> getById(Integer walletId);
    Integer save(Wallet wallet);
    Integer update(Wallet wallet);
    Integer delete(Integer walletId);
}
