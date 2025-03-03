package org.rodr.app;

import io.quarkus.cache.CacheManager;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rodr.infrastructure.restapi.swop.SWOPRestClient;
import org.rodr.infrastructure.restapi.swop.dto.ExchangeRateDTO;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class CurrenciesIntegrationTest {
    public static final double CONVERSION_RATE = 1.0;
    @InjectMock
    @RestClient
    SWOPRestClient swopRestClient;
    @Inject
    CacheManager cacheManager;

    @BeforeEach
    public void init() {
        clearAllCaches();
    }

    private void clearAllCaches() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name)
                .ifPresent(c -> c.invalidateAll().await().indefinitely()));
    }

    @Test
    public void testGetCurrencies() {
        when(swopRestClient.getExchangeRates()).thenReturn(List.of(
                new ExchangeRateDTO("EUR", "USD", CONVERSION_RATE),
                new ExchangeRateDTO("BRL", "USD", CONVERSION_RATE)
        ));

        given()
                .when().get("/currencies")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("list.size()", is(3))
                .body("[0].code", equalTo("BRL"))
                .body("[1].code", equalTo("EUR"))
                .body("[2].code", equalTo("USD"));
    }
}