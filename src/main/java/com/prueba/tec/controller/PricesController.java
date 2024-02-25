package com.prueba.tec.controller;

import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PricesRepository;
import com.prueba.tec.service.IPricesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/prices")
public class PricesController {
    private final IPricesService pricesService;
    private final PricesRepository pricesRepository;

    // Método para consultar la tabla prices
    @GetMapping(value = "/{key}")
    public ResponseEntity<GetPricesResponse> getPrices(@PathVariable(value = "key", required = true) String keyPrices) throws SQLException {

        log.info("Start process GET /prices/{key}");
        if(keyPrices.isEmpty()){
            log.error("El valor proporcionado de keyPrices es incorrecto");
        }

        // Inicializacion de tabla y campos de ejemplo
        pricesRepository.createTablePrices();
        pricesRepository.insertRows();

        GetPricesResponse pricesResponse = pricesService.getPricesResponse(keyPrices);
        if(pricesResponse == null){
            log.error("Hubo un problema al consultar la tabla prices");
        }
        return ResponseEntity.ok(pricesResponse);
    }
}
