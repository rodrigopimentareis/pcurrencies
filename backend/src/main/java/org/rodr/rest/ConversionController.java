package org.rodr.rest;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.rodr.app.conversion.ConversionRateService;
import org.rodr.rest.dto.ConversionDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Path("/conversions")
public class ConversionController {

    @Inject
    ConversionRateService exchangeRateService;

    @GET
    @Path("/{base}/{quote}")
    @Produces(MediaType.APPLICATION_JSON)
    public ConversionDTO convert(
            @PathParam("base") String base,
            @PathParam("quote") String quote,
            @QueryParam("amount") double amount
    ) {
        if (amount <= 0) {
            throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Amount must be greater than 0")
                    .build());
        }

        double conversion = exchangeRateService.convert(base, quote, amount);
        return new ConversionDTO(base, quote, new BigDecimal(conversion).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}
