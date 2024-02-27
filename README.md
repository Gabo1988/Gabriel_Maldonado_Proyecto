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


## Endpoint

### GET /prices

This endpoint retrieves price data based on the provided parameters.

#### Request Parameters

- `applicationDate` (required): The date and time of the price application.
- `productId` (required): The ID of the product.
- `brandId` (required): The ID of the brand.

#### Response

- `GetPricesResponse`: An object containing the retrieved price data.

## Usage

Example request:

#### GET /prices?applicationDate=2024-02-14T10:00:00&productId=35455&brandId=1

Example response:
{
  "productId": "35455",
  "brandId": "brand1",
  "priceList": "1",
  "startDate": "2024-02-14T00:00:00",
  "endDate": "2024-02-14T15:00:00",
  "price": 35.5
}

## Use Cases

The use cases used for testing are as follows:

/prices?applicationDate=2024-02-14 10:00:00&productId=35455&brandId=1

/prices?applicationDate=2024-02-14 16:00:00&productId=35455&brandId=1

/prices?applicationDate=2024-02-14 21:00:00&productId=35455&brandId=1

/prices?applicationDate=2024-02-15 10:00:00&productId=35455&brandId=1

/prices?applicationDate=2024-02-16 21:00:00&productId=35455&brandId=1

