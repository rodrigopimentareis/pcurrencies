package org.rodr.infrastructure.restapi.swop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateDTO {

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    @JsonProperty("quote")
    private double quote;

    public ExchangeRateDTO(@JsonProperty("base_currency") String baseCurrency,
                           @JsonProperty("quote_currency") String quoteCurrency,
                           @JsonProperty("quote") double quote) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.quote = quote;
    }

    public double getQuote() {
        return quote;
    }

    public void setQuote(double quote) {
        this.quote = quote;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }
}
