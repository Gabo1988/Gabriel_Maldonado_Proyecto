package com.prueba.tec.service;

import com.prueba.tec.domain.Price;
import com.prueba.tec.exception.DataNotFoundException;
import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class PriceQueryAdapter implements PriceQueryPort {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceQueryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public GetPricesResponse selectPricesByBrandIdAndProductIdAndDate(String brandId, String productId, Timestamp date) {
        List<Price> listPrices = priceRepository.selectPricesByBrandIdAndProductIdAndDate(brandId, productId, date);
        if (!listPrices.isEmpty()) {
            Price price = listPrices.get(0);
            GetPricesResponse getPricesResponse = new GetPricesResponse();
            getPricesResponse.setProductId(price.getProductId());
            getPricesResponse.setBrandId(price.getBrandId());
            getPricesResponse.setPriceList(price.getPriceList() + "");
            getPricesResponse.setStartDate(price.getStartDate());
            getPricesResponse.setEndDate(price.getEndDate());
            getPricesResponse.setPrice(price.getPrice());
            return getPricesResponse;
        } else {
            throw new DataNotFoundException("Not found data for this parameters");
        }
    }

    public void insertInitialData() {
        priceRepository.saveAll(List.of(
                new Price("1", Timestamp.valueOf("2024-02-14 00:00:00"), Timestamp.valueOf("2024-02-14 15:00:00"),
                        1, "35455", 0, 35.5, "EUR", Timestamp.valueOf("2024-02-14 10:00:00"), "user1"),
                new Price("1", Timestamp.valueOf("2024-02-14 15:30:00"), Timestamp.valueOf("2024-02-14 18:30:00"),
                        2, "35455", 1, 25.45, "EUR", Timestamp.valueOf("2024-02-14 16:00:00"), "user1"),
                new Price("1", Timestamp.valueOf("2024-02-14 17:00:00"), Timestamp.valueOf("2024-02-15 05:00:00"),
                        3, "35455", 1, 30.5, "EUR", Timestamp.valueOf("2024-02-14 21:00:00"), "user2"),
                new Price("1", Timestamp.valueOf("2024-02-15 09:00:00"), Timestamp.valueOf("2024-02-15 23:59:59"),
                        4, "35455", 1, 38.95, "EUR", Timestamp.valueOf("2024-02-15 10:00:00"), "user1"),
                new Price("1", Timestamp.valueOf("2024-02-16 11:00:00"), Timestamp.valueOf("2024-02-16 23:59:59"),
                        5, "35455", 1, 40.95, "EUR", Timestamp.valueOf("2024-02-16 21:00:00"), "user1")
        ));
    }

    public void printAllPrices() {
        List<Price> prices = priceRepository.findAll();
        for (Price price : prices) {
            System.out.println(price.toString());
        }
    }
}
