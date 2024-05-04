package com.example.ecomerseshop.mapper;

import com.example.ecomerseshop.dto.Wallet;
import com.example.ecomerseshop.entity.WalletEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WalletMapper {

    default Wallet toDto(WalletEntity wallet) {
        Wallet wallet1 = new Wallet();
        wallet1.setAmount(wallet.getAmount());
        wallet1.setUserId(wallet.getUser().getId());
        return wallet1;
    };
    WalletEntity toEntity(Wallet wallet);
}
