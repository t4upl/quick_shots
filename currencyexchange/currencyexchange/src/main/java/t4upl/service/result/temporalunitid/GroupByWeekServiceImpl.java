package t4upl.service.result.temporalunitid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Value;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;

public class GroupByWeekServiceImpl implements GroupByTemporalUnitIdService {

  @Override
  public Map<Integer, List<CurrencyValue>> getTemporalUnitIdToCurrencyValueList(
    List<ResultElement> resultListElement) {

    List<List<CurrencyValue>> currencyValuesInYear = resultListElement.stream()
      .sorted(Comparator.comparing(ResultElement::getTemporalUnitId))
      .map(ResultElement::getCurrencyValueList)
      .collect(Collectors.toList());

    List<CurrencyValueWithWeek> currencyValueWithWeekList = currencyValuesInYear.stream()
      .map(this::toCurrencyValueWithWeekList)
      .flatMap(Collection::stream)
      .collect(Collectors.toList());

    Map<Integer, List<CurrencyValueWithWeek>> weekToCurrencyValueWithWeek = currencyValueWithWeekList.stream()
      .collect(Collectors.groupingBy(CurrencyValueWithWeek::getWeek));
    return weekToCurrencyValueWithWeek.entrySet().stream()
      .collect(Collectors.toMap(Entry::getKey, entry -> toCurrencyValue(entry.getValue())));
  }

  private List<CurrencyValue> toCurrencyValue(List<CurrencyValueWithWeek> currencyValueWithWeekList) {
    return currencyValueWithWeekList.stream().map(CurrencyValueWithWeek::getCurrencyValue).collect(Collectors.toList());
  }

  private List<CurrencyValueWithWeek> toCurrencyValueWithWeekList(List<CurrencyValue> currencyValueList) {
    List<CurrencyValueWithWeek> currencyValueWithWeekList = new ArrayList<>();
    int week = 1;
    LocalDate beginningOfTheWeek = currencyValueList.get(0).getDay();
    for (CurrencyValue currencyValue : currencyValueList) {
      if (currencyValue.getDay().isBefore(beginningOfTheWeek.plusDays(7))) {
        currencyValueWithWeekList.add(new CurrencyValueWithWeek(currencyValue, week));
        continue;
      }
      week++;
      beginningOfTheWeek = currencyValue.getDay();
      currencyValueWithWeekList.add(new CurrencyValueWithWeek(currencyValue, week));
    }
    return currencyValueWithWeekList;
  }

  @Value
  private class CurrencyValueWithWeek {
    CurrencyValue currencyValue;
    int week;

  }
}
