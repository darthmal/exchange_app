# Currency Exchange API

A Spring Boot application that provides real-time currency exchange rates and conversion using the ExchangeRate API.

## Features

### Currency Exchange
- Real-time currency conversion
- Support for multiple international currencies
- Detailed exchange rate information
- Last update time tracking

### API Endpoints
1. Currency Exchange Endpoint
   - `POST /api/exchange/convert` - Convert between currencies

### Error Handling
- Invalid source currency validation
- Invalid target currency validation
- Amount validation
- API connection error handling

## Technical Stack

- Java 17
- Spring Boot 3.x
- ExchangeRate API Integration
- OpenAPI/Swagger for documentation

## Prerequisites

1. Java 17 or higher
2. Maven 3.6 or higher
3. ExchangeRate API key

## Setup Instructions

### 1. API Key Setup

1. Get your API key from ExchangeRate API
2. Configure the `.env` file in the project root:
   ```properties
   PORT=8080 or any other unused port
   API_KEY=your_exchangerate_api_key


2. Project Setup
- Clone the repository
- Open in your preferred IDE
- Copy .env.example to .env and update the API key
- Run the application:
- Find the main application class
- Right-click and select Run
- Or use Maven: mvn spring-boot:run
- The application will start on http://localhost:8080 (or the port set in .env)
- Access Swagger UI: http://localhost:8080/exchange-app/swagger-ui.html
### API Usage
Currency Conversion

        Endpoint: POST /api/exchange/convert
        Request Body:
        json
        CopyInsert
        {
          "sourceCurrency": "USD",
          "targetCurrency": "EUR",
          "amount": 100.00
        }
        Response:
        json
        CopyInsert
        {
          "sourceCurrency": "USD",
          "targetCurrency": "EUR",
          "sourceAmount": 100.00,
          "convertedAmount": 91.80,
          "exchangeRate": 0.918,
          "lastUpdateTime": "Thu, 13 Mar 2025 00:00:01 +0000"
        }



### Error Responses
The API provides detailed error messages for:
```properties
    Invalid source currency
    Invalid target currency
    Invalid amount
    API connection issues