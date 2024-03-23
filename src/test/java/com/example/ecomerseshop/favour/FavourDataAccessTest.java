package com.example.ecomerseshop.favour;

import com.example.ecomerseshop.dto.Favour;
import com.example.ecomerseshop.dto.PageFilter;
import com.example.ecomerseshop.entity.FavourEntity;
import com.example.ecomerseshop.mapper.FavourMapper;
import com.example.ecomerseshop.repository.FavourRepository;
import com.example.ecomerseshop.dao.implementation.FavourDataAccessImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavourDataAccessTest {

    @Mock
    private FavourRepository favourRepository;

    @Mock
    private FavourMapper favourMapper;

    @InjectMocks
    private FavourDataAccessImpl favourDataAccess;

    private FavourEntity mockFavourEntity;
    private Favour mockFavour;
    private PageFilter mockPageFilter;

    @BeforeEach
    void setUp() {
        mockFavourEntity = new FavourEntity();
        mockFavourEntity.setId(1);
        mockFavourEntity.setTitle("Mock Favour");
        mockFavourEntity.setDescription("Mock Favour Description");

        mockFavour = new Favour();
        mockFavour.setId(2);
        mockFavour.setTitle("Mock Favour");
        mockFavour.setDescription("Mock Favour Description");

        mockPageFilter = new PageFilter();
    }

    @Test
    void testGetAllByFilter() {
        List<FavourEntity> mockFavourEntities = new ArrayList<>();
        mockFavourEntities.add(mockFavourEntity);
        Page<FavourEntity> mockPage = mock(Page.class);
        when(mockPage.getContent()).thenReturn(mockFavourEntities);
        when(mockPage.getTotalElements()).thenReturn(1L);

        when(favourRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);
        when(favourMapper.toDto(any(FavourEntity.class))).thenReturn(mockFavour);

        com.example.ecomerseshop.dto.Page<Favour> result = favourDataAccess.getAllByFilter(mockPageFilter);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetById() {
        when(favourRepository.findById(1)).thenReturn(Optional.of(mockFavourEntity));
        when(favourMapper.toDto(any(FavourEntity.class))).thenReturn(mockFavour);

        Optional<Favour> result = favourDataAccess.getById(1);

        assertEquals(mockFavour, result.orElse(null));
    }

    @Test
    void testSave() {
        when(favourRepository.save(any(FavourEntity.class))).thenReturn(mockFavourEntity);

        Integer result = favourDataAccess.save(mockFavour);

        assertEquals(mockFavourEntity.getId().intValue(), result.intValue());
    }

    @Test
    void testUpdate() {
        when(favourRepository.findById(anyInt())).thenReturn(Optional.of(mockFavourEntity));
        when(favourRepository.save(any(FavourEntity.class))).thenReturn(mockFavourEntity);

        Integer result = favourDataAccess.update(mockFavour);

        assertEquals(mockFavourEntity.getId().intValue(), result.intValue());
    }

    @Test
    void testDelete() {
        Integer favourId = 1;
        favourDataAccess.delete(favourId);

        verify(favourRepository, times(1)).deleteById(favourId);
    }
}

