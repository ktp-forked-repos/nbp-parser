package pl.parser.nbp.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import pl.parser.nbp.utils.CurrencyConverter;

import java.math.BigDecimal;

@XStreamAlias(value = "pozycja")
public final class Position {
    @XStreamAlias(value = "nazwa_waluty")
    private String currencyName;
    @XStreamAlias(value = "przelicznik")
    private long factor;
    @XStreamAlias(value = "kod_waluty")
    private CurrencyCode currencyCode;
    @XStreamAlias(value = "kurs_kupna")
    @XStreamConverter(value = CurrencyConverter.class)
    private BigDecimal buyingRate;
    @XStreamAlias(value = "kurs_sprzedazy")
    @XStreamConverter(value = CurrencyConverter.class)
    private BigDecimal sellingRate;

    public String getCurrencyName() {
        return currencyName;
    }

    public long getFactor() {
        return factor;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getBuyingRate() {
        return buyingRate;
    }

    public BigDecimal getSellingRate() {
        return sellingRate;
    }
}
