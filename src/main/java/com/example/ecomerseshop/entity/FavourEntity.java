package com.example.ecomerseshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "favours")
public class FavourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "title")
    @Size(min = 10, max = 150)
    String title;

    @Column(name = "description")
    @Size(min = 100, max = 500)
    String description;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}