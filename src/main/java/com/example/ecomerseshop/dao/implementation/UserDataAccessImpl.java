package com.example.ecomerseshop.dao.implementation;

import com.example.ecomerseshop.dao.UserDataAccess;
import com.example.ecomerseshop.dto.Page;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.dto.User;
import com.example.ecomerseshop.entity.UserEntity;
import com.example.ecomerseshop.mapper.UserMapper;
import com.example.ecomerseshop.repository.UserRepository;
import com.example.ecomerseshop.utils.LimitOffsetPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class UserDataAccessImpl implements UserDataAccess {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<User> getAllByFilter(PageFilter userFilter) {
        Specification<UserEntity> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(userFilter.getCreatedAtStart() != null) {
                Predicate predicate =
                        (Predicate) criteriaBuilder.greaterThanOrEqualTo(root
                                        .get("createdAt"),
                                criteriaBuilder.literal(userFilter.getCreatedAtStart()));
                predicates.add(predicate);
            }

            if(userFilter.getCreatedAtEnd() != null) {
                Predicate predicate =
                        (Predicate) criteriaBuilder.lessThanOrEqualTo(root
                                        .get("createdAt"),
                                criteriaBuilder.literal(userFilter.getCreatedAtStart()));
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
        });
        Pageable pageable = new LimitOffsetPageRequest(userFilter.getOffset(),
                userFilter.getLimit(),
                Sort.by("id").descending());
        org.springframework.data.domain.Page<UserEntity> userEntityPage = userRepository.findAll(specification, pageable);
        List<User> users = userEntityPage.getContent().stream().map(userMapper::toDto).toList();
        return new com.example.ecomerseshop.dto.Page<>(users,
                userEntityPage.getTotalElements(),
                userFilter.getOffset(),
                userFilter.getLimit());
    }

    @Override
    public Optional<User> getById(Integer userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::toDto);
    }

    @Override
    public Integer save(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(userEntity).getId();
    }

    @Override
    public Integer update(User user) {
        var userEntity = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Не был найден пользователь с таким идентификатором!"));

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(userEntity).getId();
    }

    @Override
    public Integer delete(Integer userId) {
        userRepository.deleteById(userId);
        return userId;
    }
}
