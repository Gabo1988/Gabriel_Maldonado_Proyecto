package com.prueba.tec.service;

import com.prueba.tec.domain.Price;
import com.prueba.tec.exception.DataNotFoundException;
import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceQueryAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceQueryAdapter priceQueryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectPricesByBrandIdAndProductIdAndDate_Found() {
        // Arrange
        String brandId = "1";
        String productId = "35455";
        Timestamp date = Timestamp.valueOf("2024-02-14 00:00:00");
        Price price = new Price();
        price.setBrandId(brandId);
        price.setProductId(productId);
        price.setPriceList(1);
        price.setStartDate(date);
        price.setEndDate(date);
        price.setPrice(35.5);
        when(priceRepository.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, date)).thenReturn(List.of(price));

        // Act
        GetPricesResponse response = priceQueryAdapter.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, date);

        // Assert
        assertNotNull(response);
        assertEquals(brandId, response.getBrandId());
        assertEquals(productId, response.getProductId());
        assertEquals("1", response.getPriceList());
        assertEquals(date, response.getStartDate());
        assertEquals(date, response.getEndDate());
        assertEquals(35.5, response.getPrice());
    }

    @Test
    void selectPricesByBrandIdAndProductIdAndDate_NotFound() {
        // Arrange
        String brandId = "1";
        String productId = "35455";
        Timestamp date = Timestamp.valueOf("2024-02-14 00:00:00");
        when(priceRepository.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, date)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> priceQueryAdapter.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, date));
    }

    @Test
    public void testInsertInitialData() {
        // Test data
        List<Price> prices = List.of(
                new Price("1", Timestamp.valueOf("2024-02-14 00:00:00"), Timestamp.valueOf("2024-02-14 15:00:00"),
                        1, "35455", 0, 35.5, "EUR", Timestamp.valueOf("2024-02-14 10:00:00"), "user1"),
                new Price("1", Timestamp.valueOf("2024-02-14 15:30:00"), Timestamp.valueOf("2024-02-14 18:30:00"),
                        2, "35455", 1, 25.45, "EUR", Timestamp.valueOf("2024-02-14 16:00:00"), "user1"),
                new Price("1", Timestamp.valueOf("2024-02-14 17:00:00"), Timestamp.valueOf("2024-02-15 05:00:00"),
                        3, "35455", 1, 30.5, "EUR", Timestamp.valueOf("2024-02-14 21:00:00"), "user2"),
                new Price("1", Timestamp.valueOf("2024-02-15 09:00:00"), Timestamp.valueOf("2024-02-15 23:59:59"),
                        4, "35455", 1, 38.95, "EUR", Timestamp.valueOf("2024-02-15 10:00:00"), "user1"),
                new Price("1", Timestamp.valueOf("2024-02-16 11:00:00"), Timestamp.valueOf("2024-02-16 23:59:59"),
                        5, "35455", 1, 40.95, "EUR", Timestamp.valueOf("2024-02-16 21:00:00"), "user1")
        );

        // Invoke the method
        priceQueryAdapter.insertInitialData();

        // Verify that the saveAll method of PriceRepository was called with the correct list of prices
        verify(priceRepository).saveAll(prices);
    }

    @Test
    void printAllPrices() {
        // Arrange
        List<Price> prices = Collections.singletonList(new Price());
        when(priceRepository.findAll()).thenReturn(prices);

        // Act
        priceQueryAdapter.printAllPrices();

        // Assert
        verify(priceRepository, times(1)).findAll();
    }
}
