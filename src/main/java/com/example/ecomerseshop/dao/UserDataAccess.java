package com.example.ecomerseshop.dao;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.User;

import java.util.Optional;

public interface UserDataAccess {
    Page<User> getAllByFilter(PageFilter userFilter);
    Optional<User> getById(Integer userId);
    Integer save(User user);

    Integer update(User user, Integer userId);

    Integer delete(Integer userId);
}
