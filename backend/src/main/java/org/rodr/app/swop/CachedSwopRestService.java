package org.rodr.app.swop;


import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.rodr.app.conversion.model.ExchangeRate;
import org.rodr.infrastructure.restapi.swop.SWOPRestClient;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CachedSwopRestService {

    private static final Logger LOG = Logger.getLogger(CachedSwopRestService.class);

    @Inject
    @RestClient
    SWOPRestClient exchangeRateClient;

    @CacheResult(cacheName = "exchange-rates-cache")
    public List<ExchangeRate> getCachedRates() {
        LOG.info("Fetching exchange rates from API");
        return exchangeRateClient.getExchangeRates().stream()
                .map(dto -> new ExchangeRate(dto.getBaseCurrency(), dto.getQuoteCurrency(), dto.getQuote()))
                .collect(Collectors.toList());
    }
}
