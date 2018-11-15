package pl.parser.nbp.utils;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import pl.parser.nbp.model.ExchangeTable;

import static java.util.Objects.nonNull;

public class XmlUtils {

    public static ExchangeTable parseExchangeTable(String xml) {
        XStream parser = new XStream(new StaxDriver());
        parser.processAnnotations(new Class[]{ExchangeTable.class});
        ExchangeTable exchangeTable = (ExchangeTable) parser.fromXML(xml);
        return nonNull(exchangeTable) ? exchangeTable : null;
    }
}
