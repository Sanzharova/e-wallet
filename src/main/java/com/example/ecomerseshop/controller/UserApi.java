package com.example.ecomerseshop.controller;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserApi {
    @GetMapping("/{userId}")
    ResponseEntity<User> getUserById(@PathVariable Integer userId);

    @PostMapping
    ResponseEntity<Integer> createUser(@RequestBody User user);

    @PutMapping("/{userId}")
    ResponseEntity<Void> updateUser(@PathVariable Integer userId, @RequestBody User user);

    @DeleteMapping("/{userId}")
    ResponseEntity<Integer> deleteUser(@PathVariable Integer userId);

    @GetMapping("/filtered")
    ResponseEntity<Page<User>> getUsersByFilter(@RequestBody PageFilter pageFilter);
}
