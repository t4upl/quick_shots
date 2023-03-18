package t4upl.service.loaddata;

import static t4upl.service.CurrencyConstant.EUR;
import static t4upl.service.CurrencyConstant.Exchange.EUR_TO_PLN_KEY;
import static t4upl.service.CurrencyConstant.Exchange.USD_TO_PLN_KEY;
import static t4upl.service.CurrencyConstant.PLN;
import static t4upl.service.CurrencyConstant.USD;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;
import t4upl.service.model.Result;

public class LoadCurrenciesService {

  private final FileLoader fileLoader = new FileLoader();
  private final UsdToPlnConverter usdToPlnConverter = new UsdToPlnConverter();

  public Result load() {
    Map<Integer, List<CurrencyValue>> eurToPlnByYear = fileLoader.loadLines("eur_to_pln.xml", EUR, PLN);
    Map<Integer, List<CurrencyValue>> eurToUsdByYear = fileLoader.loadLines("eur_to_usd.xml", EUR, USD);
    Map<Integer, List<CurrencyValue>> usdToPlnByYear = usdToPlnConverter
      .convertToUsdToPln(eurToPlnByYear, eurToUsdByYear);

    return Result.builder()
      .temporalUnitName(Result.TemporalUnitName.YEAR)
      .resultElementList(getResultList(eurToPlnByYear, usdToPlnByYear))
      .build();
  }

  private List<ResultElement> getResultList(Map<Integer, List<CurrencyValue>> eurToPlnByYear,
    Map<Integer, List<CurrencyValue>> usdToPlnByYear) {
    return List.of(resultList(eurToPlnByYear, EUR_TO_PLN_KEY),
                   resultList(usdToPlnByYear, USD_TO_PLN_KEY)).stream()
      .flatMap(Collection::stream)
      .collect(Collectors.toList());
  }

  private List<ResultElement> resultList(Map<Integer, List<CurrencyValue>> currencyToPlnByYear, Exchange currencyPair) {
    return currencyToPlnByYear.entrySet().stream()
      .map(currencyToPlnByYearEntry -> ResultElement.builder()
        .exchange(currencyPair)
        .temporalUnitId(currencyToPlnByYearEntry.getKey())
        .currencyValueList(currencyToPlnByYearEntry.getValue())
        .build())
      .collect(Collectors.toList());
  }

}
