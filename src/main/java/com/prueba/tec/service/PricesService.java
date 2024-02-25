package com.prueba.tec.service;

import com.prueba.tec.model.GetPricesResponse;
import com.prueba.tec.repository.PricesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PricesService implements IPricesService{
    private final PricesRepository pricesRepository;
    @Override
    public GetPricesResponse getPricesResponse(String keyPrices) {

        log.info("Start method en capa service - getPricesResponse");

        //Ejemplo de entrada 2020-06-14-00.00.00,35455,brand1
        // Dividir la cadena por el carácter de tubería
        String[] parametros = keyPrices.split(",");

        // Validar que sean 3 los parametros obtenidos
        if(parametros.length < 3){
            log.error("La cantidad de parametros no es la adecuada");
            return new GetPricesResponse();
        }

        //Validar el tipo de datos en keyPrices
        if(!isValidInteger(parametros[1])){
            log.error("La cadena proporcionada no es Entero");
        }
        if(!isValidTimestamp(parametros[0])) {
            log.error("La cadena proporcionada no es Timestamp");
        }

        // Consulta la tabla prices
        List<GetPricesResponse> response =
                pricesRepository.selectPricesByBrandIdAndProductIdAndDate
                        (parametros[2], parametros[1], parseStringToTimestamp(parametros[0]));

        if(response.isEmpty()){
            log.error("No se encontraron registros que mostrar");
            return new GetPricesResponse();
        } else {
            return response.get(0);
        }
    }

    // Método para validar si un String representa un número integer
    public static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Método para validar si una cadena representa un timestamp válido
    public static boolean isValidTimestamp(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Timestamp parseStringToTimestamp(String fechaString) {
        // Crear un formateador de fecha y hora para analizar el String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir el String a un objeto LocalDateTime usando el formateador
        LocalDateTime fechaLocalDateTime = LocalDateTime.parse(fechaString, formatter);

        // Convertir el LocalDateTime a un Timestamp y devolverlo
        return Timestamp.valueOf(fechaLocalDateTime);
    }
}
