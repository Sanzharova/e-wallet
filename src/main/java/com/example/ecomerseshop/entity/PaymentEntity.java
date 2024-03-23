package com.example.ecomerseshop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @Column(name = "created_at")
    Timestamp created_at;

    @Column(name = "updated_at")
    Timestamp updated_at;

    @Column(name = "sum_of_favour")
    BigDecimal sumOfFavour;

    @Column(name = "account_check")
    String bankBookCheck;

    @Column(name = "is_checked")
    Boolean isChecked;

    @Column(name = "is_finished")
    Boolean finished;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "favour_id")
    private FavourEntity favour;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

}