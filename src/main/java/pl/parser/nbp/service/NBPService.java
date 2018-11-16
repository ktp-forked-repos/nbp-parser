package pl.parser.nbp.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.parser.nbp.http.AbstractHttpClient;
import pl.parser.nbp.model.ExchangeTable;
import pl.parser.nbp.model.TableType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pl.parser.nbp.constant.CustomName.XML_EXTENSION;
import static pl.parser.nbp.constant.ServiceEndpoint.EXCHANGE_TABLE_ENDPOINT;
import static pl.parser.nbp.utils.FileNameUtils.*;
import static pl.parser.nbp.utils.XmlUtils.parseExchangeTable;

@Service
public class NBPService extends AbstractHttpClient {

    @Value("${nbp.base.url}")
    private String baseUrl;

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    @Override
    protected String getServiceName() {
        return "NBPService";
    }

    public List<ExchangeTable> retrieveExchangeTables(TableType tableType, LocalDate start, LocalDate end) throws IOException {
        List<String> filePatterns = retrieveExchangeRateFileNames(tableType, start, end);
        List<ExchangeTable> exchangeTables = new ArrayList<>();
        for(String pattern : filePatterns) {
            exchangeTables.add(retrieveExchangeTable(pattern));
        }
        return exchangeTables;
    }

    private List<String> retrieveExchangeRateFileNames(TableType tableType, LocalDate start, LocalDate end) throws IOException {
        List<String> files = new ArrayList<>();
        for(LocalDate year = start; year.getYear() <= end.getYear(); year = year.plusYears(1)) {
            files.addAll(filterByDate(findFileNames(tableType, getAnnualListFileName(year)), start, end));
        }
        return files;
    }

    private ExchangeTable retrieveExchangeTable(String tableName) throws IOException {
        String response = get(EXCHANGE_TABLE_ENDPOINT + tableName + XML_EXTENSION);
        return parseExchangeTable(response);
    }

    private List<String> findFileNames(TableType tableType, String directory) throws IOException {
        return findFilePatterns(tableType, get(EXCHANGE_TABLE_ENDPOINT + directory));
    }
}
