package org.rodr.rest.dto;

import java.util.Objects;

public class ConversionDTO {
    private String baseCurrency;
    private String quoteCurrency;
    private Double quote;

    public ConversionDTO(String baseCurrency, String quoteCurrency, Double quote) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.quote = quote;
    }

    // Getters and Setters
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConversionDTO that = (ConversionDTO) o;
        return Objects.equals(baseCurrency, that.baseCurrency) && Objects.equals(quoteCurrency, that.quoteCurrency) && Objects.equals(quote, that.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, quoteCurrency, quote);
    }

    @Override
    public String toString() {
        return "ConversionDTO{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", quoteCurrency='" + quoteCurrency + '\'' +
                ", quote=" + quote +
                '}';
    }
}
