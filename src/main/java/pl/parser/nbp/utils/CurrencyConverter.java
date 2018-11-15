package pl.parser.nbp.utils;


import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;

import java.math.BigDecimal;

public class CurrencyConverter extends BigDecimalConverter {

    @Override
    public boolean canConvert(Class type) {
        return true;
    }

    @Override
    public Object fromString(String str) {
        return new BigDecimal(str.replaceAll(",", "."));
    }
}
