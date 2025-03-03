package org.rodr.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.rodr.app.currencies.CurrenciesService;
import org.rodr.rest.dto.CurrencyDTO;

import java.util.Comparator;
import java.util.List;

@Path("/currencies")
public class CurrencyController {

    @Inject
    CurrenciesService currenciesService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyDTO> getAvailableCurrencies() {
        return currenciesService.getAvailableCurrencies().stream()
                .map(c -> new CurrencyDTO(c.getCode()))
                .sorted(Comparator.comparing(CurrencyDTO::getCode))
                .toList();
    }
}
