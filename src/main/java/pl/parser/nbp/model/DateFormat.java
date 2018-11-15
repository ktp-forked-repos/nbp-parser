package pl.parser.nbp.model;


public enum DateFormat {
    ISO8601("yyyy-MM-dd"),
    FILE_PATTERN("yyMMdd");

    private String dateFormat;

    DateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
