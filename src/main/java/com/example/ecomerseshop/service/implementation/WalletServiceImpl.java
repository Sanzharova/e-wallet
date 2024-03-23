package com.example.ecomerseshop.service.implementation;

import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.Wallet;
import com.example.ecomerseshop.entity.WalletEntity;
import com.example.ecomerseshop.mapper.WalletMapper;
import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.repository.WalletRepository;
import com.example.ecomerseshop.service.WalletService;
import com.example.ecomerseshop.utils.LimitOffsetPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserRepository userRepository;

    @Override
    public Page<Wallet> getAllByFilter(PageFilter walletFilter) {
        Specification<WalletEntity> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(walletFilter.getCreatedAtStart() != null) {
                Predicate predicate =
                        (Predicate) criteriaBuilder.greaterThanOrEqualTo(root
                                        .get("createdAt"),
                                criteriaBuilder.literal(walletFilter.getCreatedAtStart()));
                predicates.add(predicate);
            }

            if(walletFilter.getCreatedAtEnd() != null) {
                Predicate predicate =
                        (Predicate) criteriaBuilder.lessThanOrEqualTo(root
                                        .get("createdAt"),
                                criteriaBuilder.literal(walletFilter.getCreatedAtStart()));
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
        });
        Pageable pageable = new LimitOffsetPageRequest(walletFilter.getOffset(),
                walletFilter.getLimit(),
                Sort.by("id").descending());
        org.springframework.data.domain.Page<WalletEntity> walletEntityPage = walletRepository.findAll(specification, pageable);
        List<Wallet> wallets = walletEntityPage.getContent().stream().map(walletMapper::toDto).toList();
        return new com.example.ecomerseshop.dto.Page<>(wallets,
                walletEntityPage.getTotalElements(),
                walletFilter.getOffset(),
                walletFilter.getLimit());
    }

    @Override
    public Optional<Wallet> getById(Integer walletId) {
        return walletRepository
                .findById(walletId)
                .map(walletMapper::toDto);
    }

    @Override
    public Integer save(Wallet wallet) {
        WalletEntity walletEntity = new WalletEntity();

        var user = userRepository
                .findById(wallet.getUserId())
                .orElseThrow(() -> new RuntimeException("Не был найден пользователь с таким идентификатором!"));

        walletEntity.setAmount(wallet.getAmount());
        walletEntity.setUser(user);

        return walletRepository.save(walletEntity).getId();
    }

    @Override
    public Integer update(Wallet wallet) {
        WalletEntity walletEntity = walletRepository
                                .findById(wallet.getId())
                                .orElseThrow(() -> new RuntimeException("Не был найден кошелек с таким идентификатором!"));

        var user = userRepository
                .findById(wallet.getUserId())
                .orElseThrow(() -> new RuntimeException("Не был найден пользователь с таким идентификатором!"));

        walletEntity.setAmount(wallet.getAmount());
        walletEntity.setUser(user);

        return walletRepository.save(walletEntity).getId();
    }

    @Override
    public Integer delete(Integer walletId) {
       walletRepository.deleteById(walletId);
       return walletId;
    }
}
