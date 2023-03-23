package t4upl.service.print;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import t4upl.service.Commons;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.model.Result;
import t4upl.service.model.Result.TemporalUnitName;
import t4upl.service.model.ResultElement;

public class ConsolePrinter {

  private final Commons commons = new Commons();

  public void print(Result result, String title) {
    List<Entry<Exchange, List<ResultElement>>> exchangeToResultElementListSortedByExchange = commons
      .getExchangeToResultElementListSortedByExchange(result);

    List<PrintData> printDataList = exchangeToResultElementListSortedByExchange.stream()
      .map(this::mapToPrintData)
      .collect(Collectors.toList());

    print(printDataList, result.getTemporalUnitName(), title);
  }

  private void print(List<PrintData> printDataList, TemporalUnitName temporalUnitName, String title) {
    System.out.println("--------------------------------");
    System.out.println("Title: " + title);
    System.out.println("TemporalUnitName: " + temporalUnitName);
    printDataList.forEach(this::print);
  }

  private void print(PrintData printData) {
    System.out.println("Exchange:" + printData.getExchange());
    List<ResultElement> resultElementList = printData.getResultElementList();
    IntStream.range(0, resultElementList.size()).forEach(index -> {
        ResultElement resultElement = resultElementList.get(index);
        System.out.println(getRow(index, resultElement));
      }
    );
  }

  protected String getRow(int index, ResultElement resultElement) {
    return String.format("%d: temporalUnitId: %d value: %.4f delta: %.4f", index, resultElement.getTemporalUnitId(),
      resultElement.getValue(), resultElement.getDelta());
  }

  private PrintData mapToPrintData(Entry<Exchange, List<ResultElement>> entry) {
    List<ResultElement> resultElements = entry.getValue().stream()
      .sorted(Comparator.comparing(ResultElement::getDelta))
      .collect(Collectors.toList());
    return new PrintData(entry.getKey(), resultElements);
  }


}
