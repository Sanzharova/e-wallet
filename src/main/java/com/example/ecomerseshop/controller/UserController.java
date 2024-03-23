package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.User;
import com.example.ecomerseshop.dao.UserDataAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserDataAccess userDataAccess;

    public ResponseEntity<User> getUserById(Integer userId) {
        Optional<User> userOptional = userDataAccess.getById(userId);
        return userOptional.map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Integer> createUser(User user) {
        Integer userId = userDataAccess.save(user);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateUser(Integer userId, User user) {
        if (!userId.equals(user.getId())) {
            return ResponseEntity.badRequest().build();
        }
        user.setId(userId);
        Integer updatedUserId = userDataAccess.update(user);
        if (updatedUserId != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Integer> deleteUser(Integer userId) {
        Integer deletedUserId = userDataAccess.delete(userId);
        return ResponseEntity.ok(deletedUserId);
    }

    public ResponseEntity<Page<User>> getUsersByFilter(PageFilter pageFilter) {
        Page<User> userPage = userDataAccess.getAllByFilter(pageFilter);
        return ResponseEntity.ok(userPage);
    }
}

