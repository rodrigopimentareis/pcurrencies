package org.rodr.app.swop;

import io.quarkus.cache.CacheManager;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rodr.app.conversion.model.ExchangeRate;
import org.rodr.infrastructure.restapi.swop.SWOPRestClient;
import org.rodr.infrastructure.restapi.swop.dto.ExchangeRateDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class CachedSwopRestServiceTest {

    @Inject
    CachedSwopRestService cachedSwopRestService;

    @InjectMock
    @RestClient
    SWOPRestClient exchangeRateClient;

    @Inject
    CacheManager cacheManager;

    @BeforeEach
    public void init() {
        List<ExchangeRateDTO> mockExchangeRates = List.of(
                new ExchangeRateDTO("USD", "EUR", 0.84),
                new ExchangeRateDTO("USD", "GBP", 0.75)
        );

        when(exchangeRateClient.getExchangeRates()).thenReturn(mockExchangeRates);
        clearAllCaches();
    }

    private void clearAllCaches() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name)
                .ifPresent(c -> c.invalidateAll().await().indefinitely()));
    }

    private List<ExchangeRate> getAndCheckRates() {
        List<ExchangeRate> rates = cachedSwopRestService.getCachedRates();
        assertEquals(2, rates.size());
        assertEquals("USD", rates.get(0).getBaseCurrency());
        assertEquals("EUR", rates.get(0).getQuoteCurrency());
        assertEquals(0.84, rates.get(0).getQuote(), 0.01);
        assertEquals("USD", rates.get(1).getBaseCurrency());
        assertEquals("GBP", rates.get(1).getQuoteCurrency());
        assertEquals(0.75, rates.get(1).getQuote(), 0.01);
        return rates;
    }

    @Test
    public void testCaching() {
        getAndCheckRates();
        getAndCheckRates();

        Mockito.verify(exchangeRateClient, Mockito.times(1)).getExchangeRates();
    }


    @Test
    public void testCacheExpiration() throws InterruptedException {
        getAndCheckRates();
        Thread.sleep(1000);
        getAndCheckRates();

        Mockito.verify(exchangeRateClient, Mockito.times(2)).getExchangeRates();
    }
}