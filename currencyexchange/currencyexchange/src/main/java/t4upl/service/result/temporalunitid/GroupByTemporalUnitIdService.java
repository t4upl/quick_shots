package t4upl.service.result.temporalunitid;

import java.util.List;
import java.util.Map;
import t4upl.service.CurrencyValue;
import t4upl.service.model.ResultElement;

public interface GroupByTemporalUnitIdService {

  Map<Integer, List<CurrencyValue>> getTemporalUnitIdToCurrencyValueList(List<ResultElement> resultListElement);

}
