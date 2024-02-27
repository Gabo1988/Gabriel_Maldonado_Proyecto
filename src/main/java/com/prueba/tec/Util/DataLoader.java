package com.prueba.tec.Util;

import com.prueba.tec.service.PriceQueryAdapter;
import com.prueba.tec.service.PriceQueryPort;
import com.prueba.tec.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final PriceQueryPort priceQueryPort;

    @Autowired
    public DataLoader(PriceQueryPort priceQueryPort) {
        this.priceQueryPort = priceQueryPort;
    }

    @Override
    public void run(String... args) {
        // Llama al método insertRows() del PriceService para cargar datos iniciales
        priceQueryPort.insertInitialData();
        System.out.println("BASE DE DATOS: ");
        priceQueryPort.printAllPrices();
    }
}
