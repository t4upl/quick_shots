package t4upl.service.print;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;

public class FillMissingDatesService {

  public PrintData fillMissingDatesToHaveFullYear(PrintData printData) {
    List<ResultElement> resultElementList = printData.getResultElementList().stream()
      .map(this::fillMissingDatesToHaveFullYear)
      .collect(Collectors.toList());
    return new PrintData(printData.getExchange(), resultElementList);
  }

  private ResultElement fillMissingDatesToHaveFullYear(ResultElement resultElement) {
    List<CurrencyValue> currencyValueList = resultElement.getCurrencyValueList().stream()
      .filter(x -> !isLeapDay(x.getDay()))
      .collect(Collectors.toList());
    List<CurrencyValue> newCurrencyValueList = new ArrayList<>();

    int bestMatchIndex = 0;
    List<LocalDate> daysInAYear = getDaysInAYear(resultElement);
    for (LocalDate localDate : daysInAYear) {
      CurrencyValue currencyValueBestMatch = getBestMatch(currencyValueList, bestMatchIndex);
      CurrencyValue newCurrencyValue = new CurrencyValue(localDate, currencyValueBestMatch.getCurrencyFrom(),
        currencyValueBestMatch.getCurrencyTo(), currencyValueBestMatch.getValue());
      newCurrencyValueList.add(newCurrencyValue);
      if (currencyValueBestMatch.getDay().equals(localDate)) {
        bestMatchIndex++;
      }
    }

    return ResultElement.builder()
      .currencyValueList(newCurrencyValueList)
      .temporalUnitId(resultElement.getTemporalUnitId())
      .exchange(resultElement.getExchange())
      .build();
  }

  private CurrencyValue getBestMatch(List<CurrencyValue> currencyValueList, int bestMatchIndex) {
    return bestMatchIndex < currencyValueList.size() ? currencyValueList.get(bestMatchIndex) :
      currencyValueList.get(currencyValueList.size() - 1);
  }

  private List<LocalDate> getDaysInAYear(ResultElement resultElement) {
    LocalDate start = LocalDate.of(resultElement.getCurrencyValueList().get(0).getYear(), 1, 1);
    LocalDate end = LocalDate.of(resultElement.getCurrencyValueList().get(0).getYear(), 12, 31)
      .plusDays(1);
    long numOfDaysBetween = ChronoUnit.DAYS.between(start, end);
    return IntStream.iterate(0, i -> i + 1)
      .limit(numOfDaysBetween)
      .mapToObj(start::plusDays)
      .filter(localDate -> !isLeapDay(localDate))
      .collect(Collectors.toList());
  }

  private boolean isLeapDay(LocalDate localDate) {
    return localDate.getDayOfMonth() == 29 && localDate.getMonth() == Month.FEBRUARY;
  }

}
