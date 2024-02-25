package com.prueba.tec.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.List;

import com.prueba.tec.model.GetPricesResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

@RequiredArgsConstructor
public class PricesRepositoryTest {

    @InjectMocks
    private PricesRepository pricesRepository;

    @BeforeEach
    public void setUp() {
        pricesRepository = new PricesRepository();
    }

    @Test
    public void testCreateTablePrices() throws SQLException {
        // Mock de la conexión y el statement
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        // Configurar el mock para devolver el statement
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // Llamar al método
        pricesRepository.createTablePrices();

        assertTrue(tableExists("PRICES")); // Suponiendo que tienes un método para verificar si la tabla existe
    }

    @Test
    public void testInsertPricesRow() throws SQLException {
        // Mock de la conexión a la base de datos
        Connection connectionMock = mock(Connection.class);
        // Mock del statement
        PreparedStatement statementMock = mock(PreparedStatement.class);

        // Configurar el mock de la conexión para que devuelva el mock del statement cuando se llame al método prepareStatement
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);

        // Ejecutar el método que queremos probar
        pricesRepository.insertPricesRow("1", Timestamp.valueOf("2024-02-14 00:00:00"),
                Timestamp.valueOf("2024-02-14 15:00:00"), 1, "35455", 0, 35.5,
                "EUR", Timestamp.valueOf("2024-02-14 10:00:00"), "user1");
    }

    @Test
    public void testSelectPricesByBrandIdAndProductIdAndDate() throws SQLException {
        // Mock de la conexión a la base de datos
        Connection connectionMock = mock(Connection.class);
        // Mock del statement
        PreparedStatement statementMock = mock(PreparedStatement.class);
        // Mock del conjunto de resultados
        ResultSet resultSetMock = mock(ResultSet.class);

        // Configurar el mock de la conexión para que devuelva el mock del statement cuando se llame al método prepareStatement
        when(connectionMock.prepareStatement(any(String.class))).thenReturn(statementMock);
        // Configurar el mock del statement para que devuelva el mock del conjunto de resultados cuando se llame al método executeQuery
        when(statementMock.executeQuery()).thenReturn(resultSetMock);

        // Configurar el mock del conjunto de resultados para que devuelva algunos datos ficticios cuando se llame a los métodos getXXX
        when(resultSetMock.next()).thenReturn(true, false);
        when(resultSetMock.getString("BRAND_ID")).thenReturn("1");
        when(resultSetMock.getString("PRODUCT_ID")).thenReturn("35455");
        when(resultSetMock.getString("PRICE_LIST")).thenReturn("1");
        when(resultSetMock.getTimestamp("START_DATE")).thenReturn(Timestamp.valueOf("2024-02-14 00:00:00"));
        when(resultSetMock.getTimestamp("END_DATE")).thenReturn(Timestamp.valueOf("2024-02-14 10:00:00"));
        when(resultSetMock.getDouble("PRICE")).thenReturn(35.5);

        // Insertar datos en la tabla antes de ejecutar la consulta
        pricesRepository.insertPricesRow("1", Timestamp.valueOf("2024-02-14 00:00:00"),
                Timestamp.valueOf("2024-02-14 10:00:00"), 1, "35455", 0, 35.5,
                "EUR", Timestamp.valueOf("2024-02-14 10:00:00"), "user1");

        // Ejecutar el método que queremos probar
        List<GetPricesResponse> result = pricesRepository.selectPricesByBrandIdAndProductIdAndDate("1", "35455", Timestamp.valueOf("2024-02-14 10:00:00"));

        // Verificar que el método devuelve los resultados esperados
        assertEquals(1, result.size());
        GetPricesResponse response = result.get(0);
        assertEquals("1", response.getBrandId());
        assertEquals("35455", response.getProductId());
        assertEquals("1", response.getPriceList());
        assertEquals(Timestamp.valueOf("2024-02-14 00:00:00"), response.getStartDate());
        assertEquals(Timestamp.valueOf("2024-02-14 10:00:00"), response.getEndDate());
        assertEquals(35.5, response.getPrice());
    }

    private static final String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    public static boolean tableExists(String tableName) {
        try (Connection connection = DriverManager.getConnection(url)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName.toUpperCase(), null);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyRowInserted(String brandId, Timestamp startDate, Timestamp endDate, int priceList,
                                     String productId, int priority, double price, String curr,
                                     Timestamp lastUpdate, String lastUpdateBy) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM PRICES WHERE " +
                    "BRAND_ID = '" + brandId + "' AND " +
                    "START_DATE = '" + startDate + "' AND " +
                    "END_DATE = '" + endDate + "' AND " +
                    "PRICE_LIST = " + priceList + " AND " +
                    "PRODUCT_ID = '" + productId + "' AND " +
                    "PRIORITY = " + priority + " AND " +
                    "PRICE = " + price + " AND " +
                    "CURR = '" + curr + "' AND " +
                    "LAST_UPDATE = '" + lastUpdate + "' AND " +
                    "LAST_UPDATE_BY = '" + lastUpdateBy + "'";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next(); // Si hay una fila, significa que se insertó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error, asumimos que la inserción no fue exitosa
        }
    }
}