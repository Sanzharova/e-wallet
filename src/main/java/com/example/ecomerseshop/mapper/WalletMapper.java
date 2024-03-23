package com.example.ecomerseshop.mapper;

import com.example.ecomerseshop.dto.Wallet;
import com.example.ecomerseshop.entity.WalletEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WalletMapper {

    Wallet toDto(WalletEntity wallet);
    WalletEntity toEntity(Wallet wallet);
}
