package com.prueba.tec.controller;

import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.service.IPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.prueba.tec.exception.DataNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.*;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/prices")
@Validated
public class PricesController {
    private final IPriceService priceService;

    // Método para consultar la tabla prices
    @GetMapping
    public ResponseEntity<GetPricesResponse> getPrices(
            @Valid @RequestParam(required = true, value = "applicationDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @NotBlank(message = "applicationDate cannot be blank")
            @NotNull LocalDateTime applicationDate,

            @Valid @RequestParam(required = true, value = "productId")
            @Min(value = 1, message = "productId debe ser mayor que 0")
            int productId,

            @Valid @RequestParam(required = true, value = "brandId")
            @NotBlank(message = "brandId no puede estar en blanco")
            String brandId) throws SQLException {

        log.info("Start process GET /prices?applicationDate={}&productId={}&brandId={}", applicationDate, productId, brandId);

        GetPricesResponse pricesResponse = priceService.selectPricesByBrandIdAndProductIdAndDate(brandId, productId+"", Timestamp.valueOf(applicationDate));

        if (pricesResponse == null) {
            throw new DataNotFoundException("Not found data for this parameters");
        }

        return ResponseEntity.ok(pricesResponse);
    }
}
