package com.example.ecomerseshop.mapper;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.entity.FavourEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FavourMapper {
    Favour toDto(FavourEntity favourEntity);
}
