package t4upl.service.loaddata;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import t4upl.service.Commons;
import t4upl.service.CurrencyConstant;
import t4upl.service.CurrencyValue;

class FileLoader {

  private static final Pattern DATE_PATTERN = Pattern.compile("TIME_PERIOD=\"(?<value>\\S+)\"");
  private static final Pattern VALUE_PATTERN = Pattern.compile("OBS_VALUE=\"(?<value>\\S+)\"");

  Map<Integer, List<CurrencyValue>> loadLines(String pathString, Currency currencyFrom, Currency currencyTo) {
    List<String> allLines = getAllLines(pathString);
    List<CurrencyValue> currencyValues = allLines.stream()
      .filter(this::isALineWithData)
      .filter(this::inYearlyRange)
      .map(row -> toCurrencyValue(row, currencyFrom, currencyTo))
      .collect(Collectors.toList());

    return currencyValues.stream()
      .collect(Collectors.groupingBy(CurrencyValue::getYear));
  }

  private boolean isALineWithData(String line) {
    return line.startsWith("<Obs");
  }

  private boolean inYearlyRange(String line) {
    String date = extractDataFromRow(line, DATE_PATTERN, "date");
    LocalDate day = CurrencyValue.toDay(date);
    return Commons.inRange(day.getYear(), CurrencyConstant.YEAR_FROM - 1,
      CurrencyConstant.YEAR_TO + 1);
  }

  private CurrencyValue toCurrencyValue(String row, Currency currencyFrom,
    Currency currencyTo) {
    String date = extractDataFromRow(row, DATE_PATTERN, "date");
    LocalDate day = CurrencyValue.toDay(date);
    String value = extractDataFromRow(row, VALUE_PATTERN, "value");
    Double valueDouble = Double.parseDouble(value);
    return new CurrencyValue(day, currencyFrom, currencyTo, valueDouble);
  }

  private List<String> getAllLines(String pathString) {
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = Optional.ofNullable(classLoader.getResource(pathString))
      .orElseThrow(() -> new RuntimeException("Classloader could not find resource"));
    File file = new File(resource.getFile());
    Path path = Paths.get(file.getPath());
    List<String> strings;
    try {
      strings = Files.readAllLines(path);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return strings;
  }

  private String extractDataFromRow(String row, Pattern pattern, String objectType) {
    Matcher matcher = pattern.matcher(row);
    boolean found = matcher.find();
    if (!found) {
      throw new RuntimeException(String.format("No %s matches found for row %s", objectType, row));
    }
    return matcher.group("value");
  }

}
