package com.prueba.tec.service;

import com.prueba.tec.model.GetPricesResponse;

import java.sql.Timestamp;

public interface PriceQueryPort {
    GetPricesResponse selectPricesByBrandIdAndProductIdAndDate(String brandId, String productId, Timestamp date);
    void insertInitialData();
    void printAllPrices();
}