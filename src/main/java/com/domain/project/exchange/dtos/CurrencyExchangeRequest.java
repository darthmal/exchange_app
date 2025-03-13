package com.domain.project.exchange.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyExchangeRequest {
    @NotBlank(message = "Source currency is required")
    private String sourceCurrency;

    @NotBlank(message = "Target currency is required")
    private String targetCurrency;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

}
