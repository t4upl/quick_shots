package t4upl.service.result;

import t4upl.service.model.Result.TemporalUnitName;
import t4upl.service.result.temporalunitid.DefaultGroupByTemporalUnitIdServiceImpl;
import t4upl.service.result.temporalunitid.GroupByTemporalUnitIdService;

public class MonthlyResultService extends ResultService{

  private final GroupByTemporalUnitIdService groupByTemporalUnitIdService = getGroupByTemporalUnitIdServiceInit();

  @Override
  protected TemporalUnitName getTemporalUnit() {
    return TemporalUnitName.MONTH;
  }

  @Override
  protected GroupByTemporalUnitIdService getGroupByTemporalUnitIdService() {
    return groupByTemporalUnitIdService;
  }

  private DefaultGroupByTemporalUnitIdServiceImpl getGroupByTemporalUnitIdServiceInit() {
    return new DefaultGroupByTemporalUnitIdServiceImpl(currencyValue -> currencyValue.getDay().getMonthValue());
  }


}
