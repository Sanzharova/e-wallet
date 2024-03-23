package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Wallet;
import com.example.ecomerseshop.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WalletController implements WalletApi{

    private final WalletService walletService;

    public ResponseEntity<Wallet> getWalletById(Integer walletId) {
        Optional<Wallet> walletOptional = walletService.getById(walletId);
        return walletOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Integer> createWallet(Wallet wallet) {
        Integer walletId = walletService.save(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(walletId);
    }

    public ResponseEntity<Integer> updateWallet(Integer walletId, Wallet wallet) {
        wallet.setId(walletId);
        Integer updatedId = walletService.update(wallet);
        if (!walletId.equals(updatedId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedId);
    }

    public ResponseEntity<Void> deleteWallet(Integer walletId) {
        walletService.delete(walletId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<Wallet>> getAllWallets(PageFilter pageFilter) {
        Page<Wallet> wallets = walletService.getAllByFilter(pageFilter);
        return ResponseEntity.ok(wallets);
    }
}

