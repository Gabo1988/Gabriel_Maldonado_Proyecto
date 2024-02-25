# README

# Price Query Project

This project exposes an endpoint that allows querying the database of an e-commerce platform.

## Technologies Used

- In-memory database (H2 type).
- Spring Boot.

## Database Schema

The project uses an in-memory H2 database with a pre-loaded table. The fields handled in the table are as follows:

| Field      | Description                                                                                      |
|------------|--------------------------------------------------------------------------------------------------|
| BRAND_ID   | Foreign key of the group chain.                                                                  |
| START_DATE | Start date of the price tariff validity range.                                                    |
| END_DATE   | End date of the price tariff validity range.                                                      |
| PRICE_LIST | Identifier of the applicable price tariff.                                                        |
| PRODUCT_ID | Product identifier.                                                                              |
| PRIORITY   | Price application disambiguator. If two tariffs overlap in a date range, the one with the highest priority (highest numeric value) is applied. |
| PRICE      | Final sale price.                                                                                |
| CURR       | Currency ISO.                                                                                    |

## Example Data

The database table contains the following records:

| BrandId | Start_Date          | End_Date            | Price_List | ProductId | Priority | Price | Curr | Last_Update          | Last_Update_By |
|---------|---------------------|---------------------|------------|-----------|----------|-------|------|----------------------|----------------|
| 1       | 2024-02-14 00:00:00 | 2024-02-14 15:00:00 | 1          | 35455     | 0        | 35.5  | EUR  | 2024-02-14 10:00:00  | user1          |
| 1       | 2024-02-14 15:30:00 | 2024-02-14 18:30:00 | 2          | 35455     | 1        | 25.45 | EUR  | 2024-02-14 16:00:00  | user1          |
| 1       | 2024-02-14 17:00:00 | 2024-02-15 05:00:00 | 3          | 35455     | 1        | 30.5  | EUR  | 2024-02-14 21:00:00  | user2          |
| 1       | 2024-02-15 09:00:00 | 2024-02-15 23:59:59 | 4          | 35455     | 1        | 38.95 | EUR  | 2024-02-15 10:00:00  | user1          |
| 1       | 2024-02-16 11:00:00 | 2024-02-16 23:59:59 | 5          | 35455     | 1        | 40.95 | EUR  | 2024-02-16 21:00:00  | user1          |

## Exposed Endpoint

The exposed endpoint is as follows:

#### GET /prices/{key}

Where `key` is a field composed of:

- Application date.
- Product identifier.
- Chain identifier.

### Example Usage of the Endpoint

Example request:

GET /prices/2024-02-14 10:00:00,35455,1

The response returned by this endpoint is as follows:

- Product identifier.
- Chain identifier.
- Tariff to apply.
- Application dates.
- Final price to apply.

Example response:

{
"productId": "35455",
"brandId": "1",
"priceList": "2",
"startDate": "2024-02-14T21:30:00.000+00:00",
"endDate": "2024-02-15T00:30:00.000+00:00",
"price": 25.45
}

## Use Cases

The use cases used for testing are as follows:

- /prices/2024-02-14 10:00:00,35455,1
- /prices/2024-02-14 16:00:00,35455,1
- /prices/2024-02-14 21:00:00,35455,1
- /prices/2024-02-15 10:00:00,35455,1
- /prices/2024-02-16 21:00:00,35455,1


# Classes
## PricesController

The `PricesController` class is a REST controller that handles requests related to prices. It provides an access point to query prices using the `/prices` route.

### Endpoints

- **GET /prices/{key}:** This endpoint is used to retrieve price information associated with a specific key. The key is provided as a parameter in the URL. It returns a `GetPricesResponse` object containing the price information corresponding to the provided key.

### Dependencies

- `IPricesService:` Interface that defines methods for fetching price information.
- `PricesRepository:` Repository that handles interaction with the database to fetch and store price information.

### Methods

- **getPrices:** This method handles GET requests to retrieve price information associated with a specific key. It uses the prices service (`pricesService`) to fetch price information corresponding to the provided key. It also utilizes the prices repository (`pricesRepository`) to initialize the prices table and load sample data before performing the query.

### Additional Notes

- This class uses the `@RestController` annotation to indicate that it is a REST controller.
- The `@RequestMapping("/prices")` annotation specifies the URL prefix for all requests handled by this controller.
- The `@Slf4j` annotation is used to enable event logging using the SLF4J library.
- The `@RequiredArgsConstructor` annotation from Lombok generates a constructor with all fields of the class as parameters, facilitating dependency injection.
- The `getPrices` method uses the `@GetMapping(value = "/{key}")` annotation to map GET requests to the `/prices/{key}` URL and capture the value of key as a URL parameter.
- Errors related to the absence of a provided key or issues with querying the prices table are handled by logging events using the SLF4J library.

## PricesService

The `PricesService` class is a service that handles business logic related to fetching price information. It implements the `IPricesService` interface and provides a method for fetching price information based on a provided key.

### Dependencies

- `PricesRepository:` Repository that handles interaction with the database to fetch price information.

### Methods

- **getPricesResponse:** This method receives a string representing a price key. The key is split into its components using the comma character as a separator. Then, it validates that the key has the correct format and queries the corresponding prices using the prices repository. If prices associated with the key are found, the first result encountered is returned. If no prices are found, an empty `GetPricesResponse` object is returned.

### Additional Notes

- The `@Service` annotation is used to mark this class as a service component in the Spring context.
- The `@RequiredArgsConstructor` annotation from Lombok generates a constructor with all fields of the class as parameters, facilitating dependency injection.
- Static methods are used for data validation and conversion within the service.
- Events are logged using the SLF4J library to keep track of operations performed by the service.
- The class implements the `IPricesService` interface, ensuring that it provides the required methods for fetching price information.

## PricesRepository

The `PricesRepository` class is responsible for interacting with the database to perform operations related to the prices table. It provides methods to create the table, insert rows, and select prices based on certain criteria.

### Dependencies

- **H2 in-memory database:** The class uses an H2 in-memory database to store price data.

### Methods

- **createTablePrices:** This method creates the prices table in the database using SQL. If the table already exists, it is dropped before being created again.

- **insertPricesRow:** This method inserts a new row into the prices table with the provided values. It uses a JDBC connection to execute a prepared SQL statement.

- **insertRows:** This method inserts multiple example rows into the prices table. It is used to load test data into the database.

- **selectPricesByBrandIdAndProductIdAndDate:** This method selects prices from the prices table based on the brand ID, product ID, and a given date. It uses a parameterized SQL query to select prices and returns a list of `GetPricesResponse` objects containing the selected price information.

### Annotations

- The `@Repository` annotation is used to mark this class as a repository component in the Spring context. This allows Spring to automatically detect it during component scanning and register it as a bean in the Spring container.

### Error Handling

- Error events are logged using the SLF4J library to keep track of exceptions that may occur during database interaction.

This README provides an overview of the functionality and implementation of the `PricesController`, `PricesService`, and `PricesRepository` classes.
