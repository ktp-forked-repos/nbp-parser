package pl.parser.nbp.utils;


import pl.parser.nbp.model.TableType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.parser.nbp.constant.CustomName.DIR;
import static pl.parser.nbp.constant.CustomName.TXT_EXTENSION;
import static pl.parser.nbp.model.DateFormat.FILE_PATTERN;

public class FileNameUtils {

    public static String getAnnualListFileName(LocalDate date) {
        return LocalDate.now().getYear() != date.getYear() ? DIR + String.valueOf(date.getYear()) + TXT_EXTENSION : DIR + TXT_EXTENSION;
    }

    public static List<String> findFilePatterns(TableType tableType, String searchText) {
        String filePattern = tableType.getTableType().toLowerCase() + "(\\d{3})" + "z" + "(\\d{6})";
        return Stream.of(searchText.split("\\s")).filter(line -> line.matches(filePattern)).collect(Collectors.toList());
    }

    public static List<String> filterByDate(List<String> fileNames, LocalDate start, LocalDate end) {
        return fileNames.stream().filter(name -> {
            LocalDate date = getDateFromFileName(name);
            return start.minusDays(1).isBefore(date) && end.plusDays(1).isAfter(date);}).collect(Collectors.toList());
    }

    private static LocalDate getDateFromFileName(String fileName) {
        return LocalDate.parse(fileName.split("z")[1], DateTimeFormatter.ofPattern(FILE_PATTERN.getDateFormat()));
    }
}