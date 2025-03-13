package com.domain.project.exchange.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyExchangeResponse {
    private String sourceCurrency;
    private String targetCurrency;
    private Double sourceAmount;
    private Double convertedAmount;
    private Double exchangeRate;
    private String lastUpdateTime;

}
