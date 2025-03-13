package com.domain.project.exchange.controller;

import com.domain.project.exchange.dtos.CurrencyExchangeRequest;
import com.domain.project.exchange.dtos.CurrencyExchangeResponse;
import com.domain.project.exchange.service.ExchangeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/convert")
    public ResponseEntity<CurrencyExchangeResponse> convertCurrency(@Valid @RequestBody CurrencyExchangeRequest request) throws Exception {
        CurrencyExchangeResponse response = exchangeService.convertCurrency(request);
        return ResponseEntity.ok(response);
    }
}
