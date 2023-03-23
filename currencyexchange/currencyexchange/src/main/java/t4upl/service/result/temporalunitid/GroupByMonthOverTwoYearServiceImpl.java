package t4upl.service.result.temporalunitid;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Value;
import t4upl.service.CurrencyConstant;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;

public class GroupByMonthOverTwoYearServiceImpl implements GroupByTemporalUnitIdService {

  @Override
  public Map<Integer, List<CurrencyValue>> getTemporalUnitIdToCurrencyValueList(
    List<ResultElement> resultListElement) {

    Map<Integer, List<CurrencyValue>> yearToCurrencyValueList = resultListElement.stream()
      .flatMap(resultElement -> resultElement.getCurrencyValueList().stream())
      .flatMap(currencyValue -> getYearAssigmentList(currencyValue).stream())
      .filter(this::yearsWithoutLastYearTo)
      .collect(Collectors.groupingBy(YearMonthCurrencyValueTuple::getPseudoMonth, toCurrencyValue()));
    return yearToCurrencyValueList;
  }

  private Collector<YearMonthCurrencyValueTuple, Object, List<CurrencyValue>> toCurrencyValue() {
    Function<List<YearMonthCurrencyValueTuple>, List<CurrencyValue>> toCurrencyValueFunction = list -> list.stream()
      .map(YearMonthCurrencyValueTuple::getCurrencyValue)
      .collect(Collectors.toList());
    return Collectors.collectingAndThen(Collectors.toList(), toCurrencyValueFunction);
  }

  private boolean yearsWithoutLastYearTo(YearMonthCurrencyValueTuple yearMonthCurrencyValueTuple) {
    return yearMonthCurrencyValueTuple.getYear() > CurrencyConstant.YEAR_FROM - 1
      && yearMonthCurrencyValueTuple.getYear() < CurrencyConstant.YEAR_TO;
  }

  private List<YearMonthCurrencyValueTuple> getYearAssigmentList(CurrencyValue currencyValue) {
    LocalDate day = currencyValue.getDay();
    return List.of( new YearMonthCurrencyValueTuple(day.getYear(),  day.getMonth().getValue(), currencyValue),
      new YearMonthCurrencyValueTuple(day.getYear() - 1, day.getMonth().getValue() + 12, currencyValue));
  }

  @Value
  @Getter
  private class YearMonthCurrencyValueTuple {
    int year;
    int pseudoMonth;
    CurrencyValue currencyValue;
  }

}
