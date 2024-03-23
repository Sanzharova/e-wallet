package com.example.ecomerseshop.service;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Page;

import java.util.Optional;

public interface FavourService {
    Page<Favour> getAllByFilter(PageFilter favourFilter);
    Optional<Favour> getById(Integer favourId);
    Integer save(Favour favour);
    Integer update(Favour favour);
    Integer delete(Integer favourId);
}
