package t4upl.service.result;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Value;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.CurrencyValue;
import t4upl.service.model.Result;
import t4upl.service.model.Result.TemporalUnitName;
import t4upl.service.model.ResultElement;
import t4upl.service.result.temporalunitid.GroupByTemporalUnitIdService;

public abstract class ResultService {

  protected abstract GroupByTemporalUnitIdService getGroupByTemporalUnitIdService();

  protected abstract TemporalUnitName getTemporalUnit();

  public Result calculateResult(Result result) {
    ExchangeToResultListList exchangeToResultListList = seperateByCurrency(result.getResultElementList());
    List<ResultElement> resultListElement = getResultListList(exchangeToResultListList);
    return Result.builder()
      .temporalUnitName(getTemporalUnit())
      .resultElementList(resultListElement)
      .build();
  }

  private List<ResultElement> getResultListList(ExchangeToResultListList exchangeToResultListList) {
    List<Entry<Exchange, List<ResultElement>>> exchangeToResultListByCurrency = getResult(exchangeToResultListList);
    return exchangeToResultListByCurrency.stream()
      .map(this::addExchange)
      .flatMap(Collection::stream)
      .sorted(Comparator.comparing(ResultElement::getExchange).thenComparing(ResultElement::getTemporalUnitId))
      .collect(Collectors.toList());
  }

  private List<ResultElement> addExchange(Entry<Exchange, List<ResultElement>> entry) {
    return entry.getValue().stream()
      .peek(x -> x.setExchange(entry.getKey()))
      .collect(Collectors.toList());
  }

  private List<Entry<Exchange, List<ResultElement>>> getResult(ExchangeToResultListList exchangeToResultListList) {
    return exchangeToResultListList.getExchangeToResultElementList().entrySet()
      .stream()
      .map(this::getResult)
      .collect(Collectors.toList());
  }

  private Entry<Exchange, List<ResultElement>> getResult(
    Entry<Exchange, List<ResultElement>> exchangeToResultListList) {
    Map<Integer, List<CurrencyValue>> temporalUnitIdToToCurrencyValues = getGroupByTemporalUnitIdService()
      .getTemporalUnitIdToCurrencyValueList(exchangeToResultListList.getValue());

    Exchange exchange = exchangeToResultListList.getKey();
    List<ResultElement> resultElements = toResultList(temporalUnitIdToToCurrencyValues);
    resultElements = addReferenceValue(resultElements);
    return new AbstractMap.SimpleEntry<>(exchange, resultElements);
  }

  private List<ResultElement> addReferenceValue(List<ResultElement> resultElements) {
    double referenceValue = getReferenceValue(resultElements);
    return resultElements.stream()
      .peek(x -> x.setReferenceValue(referenceValue))
      .collect(Collectors.toList());
  }

  private double getReferenceValue(List<ResultElement> resultListElement) {
    return Optional.ofNullable(resultListElement.get(0)).map(ResultElement::getValue)
      .orElseThrow(() -> new RuntimeException("Cannot set reference when input list is empty"));
  }

  private List<ResultElement> toResultList(Map<Integer, List<CurrencyValue>> monthToCurrencyValuesSorted) {
    return monthToCurrencyValuesSorted.entrySet().stream()
      .map(x -> ResultElement.builder()
        .temporalUnitId(x.getKey())
        .currencyValueList(x.getValue())
        .value(getAverage(x.getValue()))
        .build())
      .collect(Collectors.toList());
  }


  private double getAverage(List<CurrencyValue> currencyValuesInMonth) {
    return currencyValuesInMonth.stream().mapToDouble(CurrencyValue::getValue).average()
      .orElseThrow(() -> new RuntimeException("Cannot get average result"));
  }


  private ExchangeToResultListList seperateByCurrency(List<ResultElement> resultListElement) {
    Map<Exchange, List<ResultElement>> exchangeListMap = resultListElement.stream()
      .collect(Collectors.groupingBy(ResultElement::getExchange));
    return new ExchangeToResultListList(exchangeListMap);
  }

  @Value
  private class ExchangeToResultListList {
    Map<Exchange, List<ResultElement>> exchangeToResultElementList;
  }


}
