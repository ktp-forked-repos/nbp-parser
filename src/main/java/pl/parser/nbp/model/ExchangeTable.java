package pl.parser.nbp.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


@XStreamAlias(value = "tabela_kursow")
public final class ExchangeTable {
    @XStreamAlias(value = "typ")
    private TableType tableType;
    @XStreamAlias(value = "uid")
    private String uid;
    @XStreamAlias(value = "numer_tabeli")
    private String tableNumber;
    @XStreamAlias(value = "data_notowania")
    private String tradingDate;
    @XStreamAlias(value = "data_publikacji")
    private String effectiveDate;
    @XStreamImplicit(itemFieldName = "pozycja")
    private List<Position> positions;

    public TableType getTableType() {
        return tableType;
    }

    public String getUid() {
        return uid;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public List<Position> getPositions() {
        return positions;
    }
}
