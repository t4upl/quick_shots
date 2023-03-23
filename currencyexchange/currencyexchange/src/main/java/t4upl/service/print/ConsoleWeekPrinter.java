package t4upl.service.print;

import static t4upl.service.CurrencyConstant.DAY_WITH_MONTH_FORMATTER;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;

public class ConsoleWeekPrinter extends ConsolePrinter {

  @Override
  protected String getRow(int index, ResultElement resultElement) {
    List<LocalDate> localDates = resultElement.getCurrencyValueList().stream()
      .sorted(Comparator.comparingInt((CurrencyValue x) -> x.getDay().getDayOfYear()))
      .map(CurrencyValue::getDay)
      .collect(Collectors.toList());

    LocalDate smallestLocalDate = localDates.get(0);
    LocalDate biggestLocalDate = localDates.get(localDates.size() - 1);

//    return temporalIdOnly(resultElement);
    return String.format("%s from: %s to %s", super.getRow(index, resultElement),
      smallestLocalDate.format(DAY_WITH_MONTH_FORMATTER), biggestLocalDate.format(DAY_WITH_MONTH_FORMATTER));
  }

  private String temporalIdOnly(ResultElement resultElement) {
    return String.format("%d", resultElement.getTemporalUnitId());
  }

}
