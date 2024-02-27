package com.prueba.tec.service;

import com.prueba.tec.model.GetPricesResponse;

import java.sql.Timestamp;
import java.util.List;

public interface IPriceService {
    GetPricesResponse selectPricesByBrandIdAndProductIdAndDate(String brandId, String productId, Timestamp applicationDate);
}