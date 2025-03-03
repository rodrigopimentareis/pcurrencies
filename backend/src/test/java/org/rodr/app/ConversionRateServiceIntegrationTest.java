package org.rodr.app;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.rodr.infrastructure.restapi.swop.SWOPRestClient;
import org.rodr.infrastructure.restapi.swop.dto.ExchangeRateDTO;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@QuarkusTest
class ConversionRateServiceIntegrationTest {
    @InjectMock
    @RestClient
    SWOPRestClient swopRestClient;

    @Test
    public void testConvert_validRequest() {
        String baseCurrency = "EUR";
        String quoteCurrency = "USD";
        double amount = 200;
        double conversionRate = 1.079301;
        when(swopRestClient.getExchangeRates()).thenReturn(List.of(new ExchangeRateDTO(baseCurrency, quoteCurrency, conversionRate)));

        given()
                .pathParam("base", baseCurrency)
                .pathParam("quote", quoteCurrency)
                .queryParam("amount", amount)
                .when().get("/conversions/{base}/{quote}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("baseCurrency", equalTo(baseCurrency))
                .body("quoteCurrency", equalTo(quoteCurrency))
                .body("quote", equalTo(215.86F));
    }

    @Test
    public void testConvert_invalidAmount() {
        given()
                .pathParam("base", "EUR")
                .pathParam("quote", "USD")
                .queryParam("amount", -100)
                .when().get("/conversions/{base}/{quote}")
                .then()
                .statusCode(400) // Bad Request
                .body(equalTo("Amount must be greater than 0"));
    }

    @Test
    public void testConvert_missingAmount() {
        given()
                .pathParam("base", "EUR")
                .pathParam("quote", "USD")
                .when().get("/conversions/{base}/{quote}")
                .then()
                .statusCode(400)
                .body(equalTo("Amount must be greater than 0"));
    }
}