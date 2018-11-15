package pl.parser.nbp.model;


import java.math.BigDecimal;

public class ExchangeRateResult {
    private final CurrencyCode currencyCode;
    private final BigDecimal exchangeRate;
    private final BigDecimal deviation;

    public ExchangeRateResult(CurrencyCode currencyCode, BigDecimal exchangeRate, BigDecimal deviation) {
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.deviation = deviation;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public BigDecimal getDeviation() {
        return deviation;
    }
}
