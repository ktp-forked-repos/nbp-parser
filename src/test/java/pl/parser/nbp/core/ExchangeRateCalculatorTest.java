package pl.parser.nbp.core;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.parser.nbp.model.ExchangeRateResult;
import pl.parser.nbp.model.ExchangeTable;
import pl.parser.nbp.model.TableType;
import pl.parser.nbp.service.NBPService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static pl.parser.nbp.model.CurrencyCode.EUR;
import static pl.parser.nbp.utils.XmlUtils.parseExchangeTable;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateCalculatorTest {

    @Mock
    private NBPService nbpService;
    @InjectMocks
    private ExchangeRateCalculator exchangeRateCalculator;


    @Test
    public void shouldCalculateMeanAndStdDeviationOfExchangeRates() throws IOException {
        LocalDate start = LocalDate.of(2017, 06, 21);
        LocalDate end   = LocalDate.of(2017, 06, 23);

        when(nbpService.retrieveExchangeTables(TableType.C, start, end)).thenReturn(preparePayLoad());

        ExchangeRateResult result = exchangeRateCalculator.calculate(EUR, start, end);

        assertEquals(BigDecimal.valueOf(4.1926), result.getExchangeRate());
        assertEquals(BigDecimal.valueOf(0.0116), result.getDeviation());
    }


    private List<ExchangeTable> preparePayLoad() throws IOException {
        String exchangeRates1 = new String(Files.readAllBytes(Paths.get("src/test/resources/c118z170621.xml")));
        String exchangeRates2 = new String(Files.readAllBytes(Paths.get("src/test/resources/c119z170622.xml")));
        String exchangeRates3 = new String(Files.readAllBytes(Paths.get("src/test/resources/c120z170623.xml")));
        return Arrays.asList(parseExchangeTable(exchangeRates1), parseExchangeTable(exchangeRates2), parseExchangeTable(exchangeRates3));
    }
}
