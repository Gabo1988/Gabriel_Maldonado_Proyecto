package com.prueba.tec.domain;

import com.prueba.tec.domain.Price;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceTest {

    @Test
    public void testPriceConstructor() {
        // Arrange
        String brandId = "brand123";
        Timestamp startDate = Timestamp.valueOf("2024-01-01 00:00:00");
        Timestamp endDate = Timestamp.valueOf("2024-12-31 23:59:59");
        int priceList = 1;
        String productId = "product123";
        int priority = 0;
        double price = 100.0;
        String curr = "USD";
        Timestamp lastUpdate = Timestamp.valueOf("2024-01-01 12:00:00");
        String lastUpdateBy = "admin";

        // Act
        Price priceObject = new Price(brandId, startDate, endDate, priceList, productId, priority, price, curr, lastUpdate, lastUpdateBy);

        // Assert
        assertEquals(brandId, priceObject.getBrandId());
        assertEquals(startDate, priceObject.getStartDate());
        assertEquals(endDate, priceObject.getEndDate());
        assertEquals(priceList, priceObject.getPriceList());
        assertEquals(productId, priceObject.getProductId());
        assertEquals(priority, priceObject.getPriority());
        assertEquals(price, priceObject.getPrice());
        assertEquals(curr, priceObject.getCurr());
        assertEquals(lastUpdate, priceObject.getLastUpdate());
        assertEquals(lastUpdateBy, priceObject.getLastUpdateBy());
    }
}
