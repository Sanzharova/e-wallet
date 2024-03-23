package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/favours")
public interface FavourApi {
    @GetMapping
    ResponseEntity<Page<Favour>> getAllFavours(@ModelAttribute PageFilter pageFilter);

    @GetMapping("/{id}")
    ResponseEntity<Favour> getFavourById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<Integer> createFavour(@RequestBody Favour favour);

    @PutMapping("/{id}")
    ResponseEntity<Void> updateFavour(@PathVariable Integer id,
                                      @RequestBody Favour favour);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteFavour(@PathVariable Integer id);
}
