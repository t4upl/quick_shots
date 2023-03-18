package t4upl.service.print;

import static t4upl.service.CurrencyConstant.DAY_WITH_MONTH_FORMATTER;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import t4upl.service.Commons;
import t4upl.service.CurrencyConstant;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.CurrencyValue;
import t4upl.service.model.Result;
import t4upl.service.model.ResultElement;

public class FileYearPrinter {

  private final Commons commons = new Commons();
  private final FillMissingDatesService fillMissingDatesService = new FillMissingDatesService();


  public void print(Result result) {
    List<Entry<Exchange, List<ResultElement>>> exchangeToResultElementListSortedByExchange = commons
      .getExchangeToResultElementListSortedByExchange(result);

    List<PrintData> printDataList = exchangeToResultElementListSortedByExchange.stream()
      .map(this::getPrintDataByYear)
      .collect(Collectors.toList());

    printDataList = printDataList.stream()
      .map(fillMissingDatesService::fillMissingDatesToHaveFullYear)
      .collect(Collectors.toList());

    print(printDataList);
  }

  private void print(List<PrintData> printDataList) {
    String pathToFile = String.format("%s/%s.csv", CurrencyConstant.PATH_TO_PRODUCED_FILES, "eur_to_pln_year");
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));
      printTitle(writer, printDataList);
      printData(writer, printDataList);
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private void printData(BufferedWriter writer, List<PrintData> printDataList) throws Exception {
    int numberOfDates = printDataList.get(0).getResultElementList().get(0).getCurrencyValueList().size();
    List<String> lines = IntStream.range(0, numberOfDates)
      .boxed()
      .map(index -> getLine(index, printDataList))
      .collect(Collectors.toList());

    for (String line : lines) {
      writer.write(line);
      writer.newLine();
    }
  }

  private String getLine(int index, List<PrintData> printDataList) {
    return IntStream.range(0, printDataList.size())
      .boxed()
      .flatMap(exchangeIndex -> getLineForExchanges(index, exchangeIndex, printDataList).stream())
      .collect(Collectors.joining(","));
  }

  private List<String> getLineForExchanges(Integer lineIndex, Integer exchangeIndex,
    List<PrintData> printDataList) {
    List<String> line = new ArrayList<>();
    List<ResultElement> resultElementList = printDataList.get(exchangeIndex).getResultElementList();
    if (exchangeIndex == 0) {
      CurrencyValue currencyValue = resultElementList.get(0)
        .getCurrencyValueList().get(lineIndex);
      line.addAll(List.of(lineIndex.toString(), currencyValue.getDay().format(DAY_WITH_MONTH_FORMATTER)));
    }


    DecimalFormat decimalFormat = new DecimalFormat("0.0000");
    List<String> values = resultElementList.stream()
      .map(resultElement -> resultElement.getCurrencyValueList().get(lineIndex))
      .map(currencyValue -> decimalFormat.format(currencyValue.getValue()))
      .map(Object::toString)
      .collect(Collectors.toList());

    line.addAll(values);
    line.add("");
    return line;
  }

  private void printTitle(BufferedWriter writer, List<PrintData> printDataList) throws IOException {
    writer.write("Title: EUR_TO_PLN");
    writer.newLine();
    writer.newLine();

    String currencyHeader = getCurrencyHeader(printDataList);
    writer.write(currencyHeader);
    writer.newLine();

    String yearHeader = getYearHeader(printDataList);
    writer.write(yearHeader);
    writer.newLine();
  }

  private String getYearHeader(List<PrintData> printDataList) {
    return IntStream.range(0, printDataList.size())
      .boxed()
      .map(exchangeIndex -> toYearHeader(exchangeIndex, printDataList))
      .collect(Collectors.joining(",", ",,", ""));
  }

  private String toYearHeader(Integer exchangeIndex, List<PrintData> printDataList) {
    return printDataList.get(exchangeIndex).getResultElementList().stream()
      .map(resultElement -> resultElement.getCurrencyValueList().get(0).getYear())
      .map(Object::toString)
      .collect(Collectors.joining(",", "", ","));
  }

  private String getCurrencyHeader(List<PrintData> printDataList) {
    int numberOfYears = printDataList.get(0).getResultElementList().size();
    return IntStream.range(0, 2 * numberOfYears + 2)
      .mapToObj(index -> toCurrencyHeader(index, numberOfYears, printDataList))
      .collect(Collectors.joining(","));
  }

  private String toCurrencyHeader(int index, int numberOfYears, List<PrintData> printDataList) {
    if (index == 2) {
      return printDataList.get(0).getExchange().toString();
    }
    if (index == 2 + numberOfYears + 1) {
      return printDataList.get(1).getExchange().toString();
    }
    return "";
  }

  private PrintData getPrintDataByYear(Entry<Exchange, List<ResultElement>> entry) {
    List<ResultElement> resultElements = entry.getValue().stream()
      .sorted(Comparator.comparing(x -> x.getCurrencyValueList().get(0).getDay().getYear()))
      .collect(Collectors.toList());
    return new PrintData(entry.getKey(), resultElements);
  }


}
