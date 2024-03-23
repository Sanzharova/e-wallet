package com.example.ecomerseshop.service;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.dto.FavourFilter;
import com.example.ecomerseshop.dto.Page;

public interface FavourService {
    Page<Favour> getAllByFilter(FavourFilter favourFilter);
    Favour getById(Integer favourId);
    Integer save(Favour favour);
    Integer update(Favour favour);
    Integer delete(Integer favourId);
}
