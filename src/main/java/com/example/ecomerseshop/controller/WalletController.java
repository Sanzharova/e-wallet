package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Wallet;
import com.example.ecomerseshop.dao.WalletDataAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WalletController implements WalletApi{

    private final WalletDataAccess walletDataAccess;

    public ResponseEntity<Wallet> getWalletById(Integer walletId) {
        Optional<Wallet> walletOptional = walletDataAccess.getById(walletId);
        return walletOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Integer> createWallet(Wallet wallet) {
        Integer walletId = walletDataAccess.save(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(walletId);
    }

    public ResponseEntity<Integer> updateWallet(Integer walletId, Wallet wallet) {
        wallet.setId(walletId);
        Integer updatedId = walletDataAccess.update(wallet);
        if (!walletId.equals(updatedId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedId);
    }

    public ResponseEntity<Void> deleteWallet(Integer walletId) {
        walletDataAccess.delete(walletId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<Wallet>> getAllWallets(PageFilter pageFilter) {
        Page<Wallet> wallets = walletDataAccess.getAllByFilter(pageFilter);
        return ResponseEntity.ok(wallets);
    }
}

