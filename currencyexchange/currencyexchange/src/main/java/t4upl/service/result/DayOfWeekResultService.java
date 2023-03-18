package t4upl.service.result;

import t4upl.service.model.Result.TemporalUnitName;
import t4upl.service.result.temporalunitid.DefaultGroupByTemporalUnitIdServiceImpl;
import t4upl.service.result.temporalunitid.GroupByTemporalUnitIdService;

public class DayOfWeekResultService extends ResultService{

  private final GroupByTemporalUnitIdService groupByTemporalUnitIdService = getGroupByTemporalUnitIdServiceInit();

  @Override
  protected GroupByTemporalUnitIdService getGroupByTemporalUnitIdService() {
    return groupByTemporalUnitIdService;
  }

  @Override
  protected TemporalUnitName getTemporalUnit() {
    return TemporalUnitName.DAY_OF_WEEK;
  }

  private DefaultGroupByTemporalUnitIdServiceImpl getGroupByTemporalUnitIdServiceInit() {
    return new DefaultGroupByTemporalUnitIdServiceImpl(currencyValue -> currencyValue.getDay().getDayOfWeek().getValue());
  }

}
