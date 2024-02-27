package com.prueba.tec.service;

import com.prueba.tec.domain.Price;
import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.service.PriceQueryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PriceServiceTest {

    @Mock
    private PriceQueryPort priceQueryPort;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSelectPricesByBrandIdAndProductIdAndDate() {
        // Test data
        String brandId = "1";
        String productId = "35455";
        Timestamp applicationDate = Timestamp.valueOf("2024-02-14 10:00:00");

        // Mocked response
        GetPricesResponse expectedResponse = new GetPricesResponse();
        expectedResponse.setProductId(productId);
        expectedResponse.setBrandId(brandId);
        // Set other fields as needed

        // Mock behavior of priceQueryPort.selectPricesByBrandIdAndProductIdAndDate
        when(priceQueryPort.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(expectedResponse);

        // Invoke the method
        GetPricesResponse actualResponse = priceService.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, applicationDate);

        // Verify that the method returned the expected response
        assertEquals(expectedResponse, actualResponse);

        // Verify that priceQueryPort.selectPricesByBrandIdAndProductIdAndDate was called with the correct arguments
        verify(priceQueryPort).selectPricesByBrandIdAndProductIdAndDate(brandId, productId, applicationDate);
    }
}
