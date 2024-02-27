package com.prueba.tec.controller;

import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.service.IPriceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(PricesController.class)
public class PricesControllerTest {

    @MockBean
    private IPriceService priceService;

    @InjectMocks
    private PricesController pricesController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPrices_Success() throws Exception {
        // Prepare data for testing
        GetPricesResponse pricesResponse = new GetPricesResponse();
        pricesResponse.setProductId("35455");
        pricesResponse.setBrandId("brand1");
        pricesResponse.setPriceList("1");
        pricesResponse.setPrice(35.5);

        // Mock the behavior of the priceService
        when(priceService.selectPricesByBrandIdAndProductIdAndDate(anyString(), anyString(), any(Timestamp.class)))
                .thenReturn(pricesResponse);

        // Perform the request to the controller
        mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .param("applicationDate", "2024-02-14 10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "brand1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"productId\":\"35455\",\"brandId\":\"brand1\",\"priceList\":\"1\",\"price\":35.5}"));
    }
}
