package org.rodr.app.conversion.model;

public class ExchangeRate {
    private final String baseCurrency;
    private final String quoteCurrency;
    private final double quote;

    public ExchangeRate(String baseCurrency, String quoteCurrency, double quote) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.quote = quote;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public double getQuote() {
        return quote;
    }
}
