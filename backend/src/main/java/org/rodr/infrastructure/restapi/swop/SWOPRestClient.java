package org.rodr.infrastructure.restapi.swop;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.rodr.infrastructure.restapi.swop.dto.ExchangeRateDTO;

import java.util.List;


@Path("/rest")
@RegisterRestClient(configKey = "swop-api")
@Produces(MediaType.APPLICATION_JSON)
@ClientHeaderParam(name = "Authorization", value = "ApiKey ${swop.api.key}")
public interface SWOPRestClient {

    @GET
    @Path("/rates")
    List<ExchangeRateDTO> getExchangeRates();
}