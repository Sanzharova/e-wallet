package com.example.ecomerseshop.service.implementation;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.dto.FavourFilter;
import com.example.ecomerseshop.entity.FavourEntity;
import com.example.ecomerseshop.mapper.FavourMapper;
import com.example.ecomerseshop.repository.FavourRepository;
import com.example.ecomerseshop.service.FavourService;
import com.example.ecomerseshop.utils.LimitOffsetPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class FavourServiceImpl implements FavourService {

    private final FavourRepository favourRepository;
    private final FavourMapper favourMapper;

    @Override
    public com.example.ecomerseshop.dto.Page<Favour> getAllByFilter(FavourFilter favourFilter) {
        Specification<FavourEntity> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(favourFilter.getCreatedAtStart() != null) {
                Predicate predicate =
                        (Predicate) criteriaBuilder.greaterThanOrEqualTo(root
                                .get("createdAt"),
                                criteriaBuilder.literal(favourFilter.getCreatedAtStart()));
                predicates.add(predicate);
            }

            if(favourFilter.getCreatedAtEnd() != null) {
                Predicate predicate =
                        (Predicate) criteriaBuilder.lessThanOrEqualTo(root
                                        .get("createdAt"),
                                criteriaBuilder.literal(favourFilter.getCreatedAtStart()));
                predicates.add(predicate);
            }

            return criteriaBuilder.and(predicates.toArray(jakarta.persistence.criteria.Predicate[]::new));
        });
        Pageable pageable = new LimitOffsetPageRequest(favourFilter.getOffset(),
                                                    favourFilter.getLimit(),
                                                    Sort.by("id").descending());
        Page<FavourEntity> favourEntityPage = favourRepository.findAll(specification, pageable);
        List<Favour> favours = favourEntityPage.getContent().stream().map(favourMapper::toDto).toList();
        return new com.example.ecomerseshop.dto.Page<>(favours,
                                                        favourEntityPage.getTotalElements(),
                                                        favourFilter.getOffset(),
                                                        favourFilter.getLimit());
    }

    @Override
    public Favour getById(Integer favourId) {
        return null;
    }

    @Override
    public Integer save(Favour favour) {
        return null;
    }

    @Override
    public Integer update(Favour favour) {
        return null;
    }

    @Override
    public Integer delete(Integer favourId) {
        return null;
    }
}