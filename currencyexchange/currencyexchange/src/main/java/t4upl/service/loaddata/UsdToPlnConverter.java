package t4upl.service.loaddata;

import static t4upl.service.CurrencyConstant.PLN;
import static t4upl.service.CurrencyConstant.USD;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import t4upl.service.CurrencyValue;

class UsdToPlnConverter {

  Map<Integer, List<CurrencyValue>> convertToUsdToPln(
    Map<Integer, List<CurrencyValue>> eurToPlnByYear,
    Map<Integer, List<CurrencyValue>> eurToUsdByYear) {

    return eurToPlnByYear.keySet().stream()
      .collect(Collectors.toMap(year -> year,
        year -> convertToUsdToPln(year, eurToPlnByYear, eurToUsdByYear)));
  }

  private List<CurrencyValue> convertToUsdToPln(Integer year,
    Map<Integer, List<CurrencyValue>> eurToPlnByYear,
    Map<Integer, List<CurrencyValue>> eurToUsdByYear) {

    List<CurrencyValue> eurToPln = eurToPlnByYear.get(year);
    List<CurrencyValue> eurToUsd = eurToUsdByYear.get(year);
    Preconditions.checkArgument(eurToPln.size() == eurToUsd.size(),
      "For year %d eurToPln and eurToUsd size differ in length");

    return IntStream.range(0, eurToPln.size())
      .mapToObj(index -> getUsdToPlnCurrencyValue(eurToPln, eurToUsd, index))
      .collect(Collectors.toList());
  }

  private CurrencyValue getUsdToPlnCurrencyValue(List<CurrencyValue> eurToPln,
    List<CurrencyValue> eurToUsd, int index) {
    CurrencyValue eurToPlnInDay = eurToPln.get(index);
    CurrencyValue eurToUsdInDay = eurToUsd.get(index);
    Preconditions.checkArgument(eurToPlnInDay.getDay().equals(eurToUsdInDay.getDay()),
      "At year %d and index %d dates differ while comparing days");
    Double usdToPlnValue = eurToPlnInDay.getValue() / eurToUsdInDay.getValue();
    return new CurrencyValue(eurToPlnInDay.getDay(), USD, PLN, usdToPlnValue);
  }

}
