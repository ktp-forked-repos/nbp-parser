package pl.parser.nbp.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "typ")
public enum TableType {
    A("a"),
    B("b"),
    C("c"),
    H("h");

    private final String tableType;

    TableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableType() {
        return tableType;
    }
}
