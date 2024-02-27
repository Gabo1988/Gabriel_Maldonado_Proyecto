package com.prueba.tec.Util;

import com.prueba.tec.Util.DataLoader;
import com.prueba.tec.service.PriceQueryPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DataLoaderTest {

    @Test
    public void testRun() {
        // Arrange
        PriceQueryPort mockPriceQueryPort = Mockito.mock(PriceQueryPort.class);
        DataLoader dataLoader = new DataLoader(mockPriceQueryPort);

        // Act
        dataLoader.run();

        // Assert
        verify(mockPriceQueryPort, times(1)).insertInitialData();
        verify(mockPriceQueryPort, times(1)).printAllPrices();
    }
}
