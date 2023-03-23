package t4upl.service.result.temporalunitid;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;

@RequiredArgsConstructor
public class DefaultGroupByTemporalUnitIdServiceImpl implements GroupByTemporalUnitIdService {

  private final Function<CurrencyValue, Integer> groupingByFunction;

  @Override
  public Map<Integer, List<CurrencyValue>> getTemporalUnitIdToCurrencyValueList(List<ResultElement> resultListElement) {
    return resultListElement.stream()
      .flatMap(x -> x.getCurrencyValueList().stream())
      .collect(Collectors.groupingBy(groupingByFunction));
  }

}
