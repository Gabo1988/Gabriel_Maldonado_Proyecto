package com.prueba.tec.controller;

import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PricesRepository;
import com.prueba.tec.service.IPricesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PricesControllerTest {

    @Mock
    private IPricesService pricesService;

    @Mock
    private PricesRepository pricesRepository;

    @InjectMocks
    private PricesController pricesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPrices_ValidKey_ReturnsOkResponse() throws Exception {
        // Arrange
        String keyPrices = "2024-02-14 10:00:00,35455,1";
        GetPricesResponse expectedResponse = new GetPricesResponse();
        when(pricesService.getPricesResponse(anyString())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<GetPricesResponse> responseEntity = pricesController.getPrices(keyPrices);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
