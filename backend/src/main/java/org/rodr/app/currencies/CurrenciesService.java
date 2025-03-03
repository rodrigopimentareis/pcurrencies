package org.rodr.app.currencies;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.rodr.app.conversion.model.ExchangeRate;
import org.rodr.app.currencies.model.Currency;
import org.rodr.app.swop.CachedSwopRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class CurrenciesService {
    private static final Logger logger = LoggerFactory.getLogger(CurrenciesService.class);

    @Inject
    CachedSwopRestService cachedExchangeRateService;

    public Set<Currency> getAvailableCurrencies() {
        logger.debug("Fetching available currencies from cached exchange rates");

        List<ExchangeRate> rates = cachedExchangeRateService.getCachedRates();
        if (rates.isEmpty()) {
            logger.warn("No exchange rates found in cache");
        }

        Set<Currency> currencies = new HashSet<>();
        rates.forEach(rate -> {
            currencies.add(new Currency(rate.getBaseCurrency()));
            currencies.add(new Currency(rate.getQuoteCurrency()));
        });

        logger.info("Found {} unique currencies", currencies.size());
        return currencies;
    }
}
