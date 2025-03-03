package org.rodr.app.conversion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.rodr.app.conversion.model.ExchangeRate;
import org.rodr.app.swop.CachedSwopRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ConversionRateService {
    private static final Logger logger = LoggerFactory.getLogger(ConversionRateService.class);


    @Inject
    CachedSwopRestService cachedExchangeRateService;

    private static Optional<ExchangeRate> findExchangeRate(String from, String to, List<ExchangeRate> rates) {
        return rates.stream()
                .filter(r -> r.getBaseCurrency().equalsIgnoreCase(from))
                .filter(r -> r.getQuoteCurrency().equalsIgnoreCase(to))
                .findFirst();
    }

    private Optional<ExchangeRate> findExchangeRate(String from, String to) {
        List<ExchangeRate> rates = this.cachedExchangeRateService.getCachedRates();

        logger.debug("Number of cached rates: {}", rates.size());

        Optional<ExchangeRate> directRate = findExchangeRate(from, to, rates);
        if (directRate.isPresent()) {
            logger.info("Direct exchange rate found for {} to {}", from, to);
            return directRate;
        }

        Optional<ExchangeRate> reverseRate = findExchangeRate(to, from, rates);
        if (reverseRate.isPresent()) {
            logger.info("Reverse exchange rate found for {} to {}", to, from);
            return reverseRate.map(rate -> new ExchangeRate(to, from, 1.0 / rate.getQuote()));
        }

        logger.warn("No exchange rate found for {} to {}", from, to);
        return Optional.empty();
    }

    public double convert(String from, String to, double amount) {
        logger.info("Converting {} from {} to {}", amount, from, to);

        Optional<ExchangeRate> rate = findExchangeRate(from, to);

        if (rate.isPresent()) {
            double convertedAmount = amount * rate.get().getQuote();
            logger.info("Converted {} {} to {} {}", amount, from, convertedAmount, to);
            return convertedAmount;
        } else {
            String errorMessage = String.format("Exchange rate not found for %s to %s", from, to);
            logger.error(errorMessage);
            throw new BadRequestException(errorMessage);
        }
    }
}
