package com.domain.project.exchange.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.domain.project.exchange.dtos.CurrencyExchangeRequest;
import com.domain.project.exchange.dtos.CurrencyExchangeResponse;
import com.domain.project.exchange.exception.ExchangeApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {
    
    @Value("${exchange.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    public ExchangeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public CurrencyExchangeResponse convertCurrency(CurrencyExchangeRequest request) throws Exception {
        try {
            String url = String.format(API_URL, apiKey, request.getSourceCurrency());
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

            if (!root.path("result").asText().equals("success")) {
                throw new ExchangeApiException("Failed to get exchange rates");
            }

            JsonNode rates = root.path("conversion_rates");
            if (!rates.has(request.getTargetCurrency())) {
                throw new ExchangeApiException("Invalid target currency: " + request.getTargetCurrency());
            }

            double rate = rates.path(request.getTargetCurrency()).asDouble();
            double convertedAmount = request.getAmount() * rate;

            CurrencyExchangeResponse exchangeResponse = new CurrencyExchangeResponse();
            exchangeResponse.setSourceCurrency(request.getSourceCurrency());
            exchangeResponse.setTargetCurrency(request.getTargetCurrency());
            exchangeResponse.setSourceAmount(request.getAmount());
            exchangeResponse.setConvertedAmount(convertedAmount);
            exchangeResponse.setExchangeRate(rate);
            exchangeResponse.setLastUpdateTime(root.path("time_last_update_utc").asText());

            return exchangeResponse;
        } catch (HttpClientErrorException.NotFound e) {
            JsonNode errorResponse;
            try {
                errorResponse = objectMapper.readTree(e.getResponseBodyAsString());
                String errorType = errorResponse.path("error-type").asText();
                if ("unsupported-code".equals(errorType)) {
                    throw new ExchangeApiException("Invalid source currency: " + request.getSourceCurrency());
                }
            } catch (Exception ex) {
                throw new Exception(ex);
            }
            throw new ExchangeApiException("Invalid source currency: " + request.getSourceCurrency());
        } catch (Exception e) {
            throw new ExchangeApiException("Error while converting currency: " + e.getMessage());
        }
    }
}
