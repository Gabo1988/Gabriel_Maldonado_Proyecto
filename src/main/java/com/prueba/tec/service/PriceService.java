package com.prueba.tec.service;

import com.prueba.tec.domain.Price;
import com.prueba.tec.exception.DataNotFoundException;
import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PriceService implements IPriceService {

    @Autowired
    private PriceQueryPort priceQueryPort;


    // Método para consultar precios por brandId, productId y applicationDate
    @Override
    public GetPricesResponse selectPricesByBrandIdAndProductIdAndDate(String brandId, String productId, Timestamp applicationDate) {
        return priceQueryPort.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, applicationDate);
    }
}
