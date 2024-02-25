package com.prueba.tec.repository;

import com.prueba.tec.model.GetPricesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Repository
public class PricesRepository {
    // URL de conexión a la base de datos en memoria H2
    private final String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    // Método para crear la tabla en la base de datos
    public void createTablePrices() {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            // Sentencia SQL para eliminar la tabla si existe
            String dropSql = "DROP TABLE IF EXISTS PRICES";
            statement.executeUpdate(dropSql);

            // Sentencia SQL para crear la tabla con las columnas necesarias
            String createSql = "CREATE TABLE PRICES (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "BRAND_ID VARCHAR(255) NOT NULL," +
                    "START_DATE TIMESTAMP NOT NULL," +
                    "END_DATE TIMESTAMP NOT NULL," +
                    "PRICE_LIST INT AUTO_INCREMENT," +
                    "PRODUCT_ID INT NOT NULL," +
                    "PRIORITY INT NOT NULL," +
                    "PRICE DECIMAL(10,2) NOT NULL," +
                    "CURR VARCHAR(255) NOT NULL," +
                    "LAST_UPDATE TIMESTAMP NOT NULL," +
                    "LAST_UPDATE_BY VARCHAR(255) NOT NULL)";
            // Ejecutar la sentencia SQL para crear la tabla
            statement.executeUpdate(createSql);
            System.out.println("Tabla creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar filas en la tabla PRICES
    public void insertPricesRow(String brandId, Timestamp startDate, Timestamp endDate, int priceList,
                                       String productId, int priority, double price, String curr,
                                       Timestamp lastUpdate, String lastUpdateBy) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, " +
                             "PRIORITY, PRICE, CURR, LAST_UPDATE, LAST_UPDATE_BY) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            // Establecer los valores de los parámetros en la sentencia preparada
            statement.setString(1, brandId);
            statement.setTimestamp(2, startDate);
            statement.setTimestamp(3, endDate);
            statement.setInt(4, priceList);
            statement.setString(5, productId);
            statement.setInt(6, priority);
            statement.setDouble(7, price);
            statement.setString(8, curr);
            statement.setTimestamp(9, lastUpdate);
            statement.setString(10, lastUpdateBy);
            // Ejecutar la sentencia SQL para insertar la fila
            statement.executeUpdate();
            System.out.println("Fila insertada exitosamente en la tabla PRICES.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar las cuatro filas en la tabla PRICES
    public void insertRows() {
        // Insertar la primera fila
        insertPricesRow("1", Timestamp.valueOf("2024-02-14 00:00:00"),
                Timestamp.valueOf("2024-02-14 15:00:00"), 1, "35455", 0, 35.5,
                "EUR", Timestamp.valueOf("2024-02-14 10:00:00"), "user1");

        // Insertar la segunda fila
        insertPricesRow("1", Timestamp.valueOf("2024-02-14 15:30:00"),
                Timestamp.valueOf("2024-02-14 18:30:00"), 2, "35455", 1, 25.45,
                "EUR", Timestamp.valueOf("2024-02-14 16:00:00"), "user1");

        // Insertar la tercera fila
        insertPricesRow("1", Timestamp.valueOf("2024-02-14 17:00:00"),
                Timestamp.valueOf("2024-02-15 05:00:00"), 3, "35455", 1, 30.5,
                "EUR", Timestamp.valueOf("2024-02-14 21:00:00"), "user2");

        // Insertar la cuarta fila
        insertPricesRow("1", Timestamp.valueOf("2024-02-15 09:00:00"),
                Timestamp.valueOf("2024-02-15 23:59:59"), 4, "35455", 1, 38.95,
                "EUR", Timestamp.valueOf("2024-02-15 10:00:00"), "user1");

        // Insertar la quinta fila
        insertPricesRow("1", Timestamp.valueOf("2024-02-16 11:00:00"),
                Timestamp.valueOf("2024-02-16 23:59:59"), 5, "35455", 1, 40.95,
                "EUR", Timestamp.valueOf("2024-02-16 21:00:00"), "user1");
    }

    public List<GetPricesResponse> selectPricesByBrandIdAndProductIdAndDate(String brandId, String productId, Timestamp date) {
        log.info("Start metodo selectPricesByBrandIdAndProductIdAndDate");
        List<GetPricesResponse> prices = new ArrayList<>();
        String query = "SELECT * FROM PRICES WHERE BRAND_ID = ? AND PRODUCT_ID = ? AND ? BETWEEN START_DATE AND END_DATE";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Establecer los parámetros BRAND_ID y PRODUCT_ID en la consulta preparada
            statement.setString(1, brandId);
            statement.setString(2, productId);

            // Establecer la fecha directamente en la consulta preparada
            statement.setTimestamp(3, date);

            // Ejecutar la consulta
            try (ResultSet resultSet = statement.executeQuery()) {
                // Iterar sobre los resultados del conjunto de resultados y crear objetos GetPricesResponse
                while (resultSet.next()) {
                    GetPricesResponse price = new GetPricesResponse();
                    price.setProductId(resultSet.getString("PRODUCT_ID"));
                    price.setBrandId(resultSet.getString("BRAND_ID"));
                    price.setPriceList(resultSet.getString("PRICE_LIST"));
                    price.setStartDate(resultSet.getTimestamp("START_DATE"));
                    price.setEndDate(resultSet.getTimestamp("END_DATE"));
                    price.setPrice(resultSet.getDouble("PRICE"));
                    prices.add(price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prices;
    }
}
