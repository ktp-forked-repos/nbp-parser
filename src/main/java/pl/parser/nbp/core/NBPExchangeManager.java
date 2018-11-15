package pl.parser.nbp.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.parser.nbp.model.CurrencyCode;
import pl.parser.nbp.model.ExchangeRateResult;

import java.time.LocalDate;

import static java.lang.String.format;

@Component
public class NBPExchangeManager {

    @Autowired
    private ExchangeRateCalculator exchangeRateCalculator;

    public void handleExchangeRate(CurrencyCode currencyCode, LocalDate start, LocalDate end) {
        try {
            ExchangeRateResult exchangeRateResult = exchangeRateCalculator.calculate(currencyCode, start, end);
            System.out.println(format("%s", currencyCode));
            System.out.println(format("%s", start));
            System.out.println(format("%s", end));
            System.out.println(format("%s", exchangeRateResult.getExchangeRate()));
            System.out.println(format("%s", exchangeRateResult.getDeviation()));
            System.exit(0);
        } catch (Exception e) {
            System.out.println(format("Unable to calculate exchange rate for currency: %s and date period: %s, %s", currencyCode, start, end));
            System.out.println("Probably that day is weekend or holiday which is not supported in NbpParser 1.0");
            System.exit(-1);
        }
    }
}
