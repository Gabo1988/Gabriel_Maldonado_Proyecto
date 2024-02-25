package com.prueba.tec.model;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPricesResponseTest {

    @Test
    public void testToString() {
        // Arrange
        GetPricesResponse response = new GetPricesResponse();
        response.setProductId("12345");
        response.setBrandId("BrandX");
        response.setPriceList("1");
        response.setStartDate(Timestamp.valueOf("2024-02-14 00:00:00"));
        response.setEndDate(Timestamp.valueOf("2024-02-14 10:00:00"));
        response.setPrice(35.5);

        // Act
        String result = response.toString();

        // Assert
        String expected = "Class GetPricesResponse {\n" +
                "    productId: 12345\n" +
                "    brandId: BrandX\n" +
                "    priceList: 1\n" +
                "    startDate: 2024-02-14 00:00:00\n" +
                "    endDate: 2024-02-14 10:00:00\n" +
                "    price: 35.5\n" +
                "}";
        assertEquals(expected, result);
    }
}