package pl.parser.nbp.core;


import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.parser.nbp.model.*;
import pl.parser.nbp.service.NBPService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
class ExchangeRateCalculator {

    @Autowired
    private NBPService nbpService;

    public ExchangeRateResult calculate(CurrencyCode currencyCode, LocalDate start, LocalDate end) throws IOException {
        List<ExchangeTable> exchanges  = retrieveExchangeTable(start, end);
        List<Position> positions       = retrievePosition(currencyCode, exchanges);
        List<BigDecimal> buyingRates   = retrieveBuyingRate(positions);
        List<BigDecimal> sellingRates  = retrieveSellingRate(positions);
        BigDecimal buyingRateAverage   = getArithmeticMean(buyingRates);
        BigDecimal sellingStdDeviation = getStandardDeviation(sellingRates);
        return new ExchangeRateResult(currencyCode, buyingRateAverage, sellingStdDeviation);
    }

    private List<ExchangeTable> retrieveExchangeTable(LocalDate start, LocalDate end) throws IOException {
        return nbpService.retrieveExchangeTables(TableType.C, start, end);
    }

    private List<Position> retrievePosition(CurrencyCode currencyCode, List<ExchangeTable> exchanges) {
        return exchanges.stream().map(table -> getPositionByCurrencyCode(currencyCode, table.getPositions())).collect(Collectors.toList());
    }

    private List<BigDecimal> retrieveBuyingRate(List<Position> positions) {
        return positions.stream().map(Objects::requireNonNull).map(Position::getBuyingRate).collect(Collectors.toList());
    }

    private List<BigDecimal> retrieveSellingRate(List<Position> positions) {
        return positions.stream().map(Objects::requireNonNull).map(Position::getSellingRate).collect(Collectors.toList());
    }

    private Position getPositionByCurrencyCode(CurrencyCode currencyCode, List<Position> positions) {
        return positions.stream().filter(position -> position.getCurrencyCode().equals(currencyCode)).findFirst().orElse(null);
    }

    private BigDecimal getArithmeticMean(List<BigDecimal> values) {
        return values.stream().reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(values.size()), MathContext.DECIMAL32).setScale(4, RoundingMode.DOWN);
    }

    private BigDecimal getStandardDeviation(List<BigDecimal> values) {
        double[] dbValues = values.stream().mapToDouble(BigDecimal::doubleValue).toArray();
        return new BigDecimal(new StandardDeviation().evaluate(dbValues)).setScale(4, RoundingMode.DOWN);
    }
}
