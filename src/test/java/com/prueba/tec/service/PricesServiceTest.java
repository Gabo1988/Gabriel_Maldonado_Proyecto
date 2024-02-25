package com.prueba.tec.service;

import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PricesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PricesServiceTest {

    @Mock
    private PricesRepository pricesRepository;

    @InjectMocks
    private PricesService pricesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock de la creación de tabla y la inserción de filas
        doNothing().when(pricesRepository).createTablePrices();
        doNothing().when(pricesRepository).insertRows();
    }

    @Test
    public void testGetPricesResponse_ValidInput_ValidResponse() throws SQLException {
        // Mock del método selectPricesByBrandIdAndProductIdAndDate
        GetPricesResponse expectedResponse = new GetPricesResponse();
        List<GetPricesResponse> list = Collections.singletonList(expectedResponse);
        when(pricesRepository.selectPricesByBrandIdAndProductIdAndDate(any(), any(), any()))
                .thenReturn(list);

        // Arrange
        String keyPrices = "2024-02-14 10:00:00,35455,1";

        // Act
        GetPricesResponse actualResponse = pricesService.getPricesResponse(keyPrices);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetPricesResponse_InvalidInput_ReturnsEmptyResponse() throws SQLException {
        // Mock del método selectPricesByBrandIdAndProductIdAndDate
        when(pricesRepository.selectPricesByBrandIdAndProductIdAndDate(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Arrange
        String keyPrices = "invalidInput";

        // Act
        GetPricesResponse actualResponse = pricesService.getPricesResponse(keyPrices);

        // Assert
        assertNotNull(actualResponse);
        assertNull(actualResponse.getProductId());
        assertNull(actualResponse.getBrandId());
        assertNull(actualResponse.getPriceList());
        assertNull(actualResponse.getStartDate());
        assertNull(actualResponse.getEndDate());
        assertNull(actualResponse.getPrice());
    }
}
