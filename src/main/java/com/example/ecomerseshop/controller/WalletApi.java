package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Wallet;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/wallets")
public interface WalletApi {
    @GetMapping("/{walletId}")
    ResponseEntity<Wallet> getWalletById(@PathVariable Integer walletId);

    @PostMapping
    ResponseEntity<Integer> createWallet(@Valid @RequestBody Wallet wallet);

    @PutMapping("/{walletId}")
    ResponseEntity<Integer> updateWallet(@PathVariable Integer walletId, @Valid @RequestBody Wallet wallet);

    @DeleteMapping("/{walletId}")
    ResponseEntity<Void> deleteWallet(@PathVariable Integer walletId);

    @GetMapping
    ResponseEntity<Page<Wallet>> getAllWallets(@ModelAttribute PageFilter pageFilter);
}
