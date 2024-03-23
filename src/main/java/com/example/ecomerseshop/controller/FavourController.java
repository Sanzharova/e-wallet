package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.service.FavourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FavourController implements FavourApi{

    private final FavourService favourService;

    public ResponseEntity<Page<Favour>> getAllFavours(PageFilter pageFilter) {
        Page<Favour> favours = favourService.getAllByFilter(pageFilter);
        return ResponseEntity.ok(favours);
    }

    public ResponseEntity<Favour> getFavourById(Integer id) {
        Optional<Favour> favourOptional = favourService.getById(id);
        return favourOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Integer> createFavour(Favour favour) {
        Integer favourId = favourService.save(favour);
        return ResponseEntity.status(HttpStatus.CREATED).body(favourId);
    }

    public ResponseEntity<Void> updateFavour(Integer id, Favour favour) {
        favour.setId(id);
        Integer updatedId = favourService.update(favour);
        if (!id.equals(updatedId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteFavour(Integer id) {
        favourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
