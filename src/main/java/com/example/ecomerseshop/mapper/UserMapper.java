package com.example.ecomerseshop.mapper;

import com.example.ecomerseshop.dto.User;
import com.example.ecomerseshop.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    User toDto (UserEntity user);
}
