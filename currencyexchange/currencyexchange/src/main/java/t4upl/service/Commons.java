package t4upl.service;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.model.Result;
import t4upl.service.model.ResultElement;

public class Commons {

  public static boolean inRange(int value, int fromExclusive, int toExclusive) {
    return value > fromExclusive && value < toExclusive;
  }


  public List<Entry<Exchange, List<ResultElement>>> getExchangeToResultElementListSortedByExchange (Result result) {
    return result
      .getResultElementList().stream()
      .collect(Collectors.groupingBy(ResultElement::getExchange)).entrySet().stream()
      .sorted(Comparator.comparing(Entry::getKey))
      .collect(Collectors.toList());
  }

  public IntSummaryStatistics getMonthRange(List<CurrencyValue> currencyValueList) {
    return currencyValueList.stream()
      .map(currencyValue -> currencyValue.getDay().getMonth().getValue())
      .collect(Collectors.summarizingInt(x -> x));
  }

}
