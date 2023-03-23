package t4upl.service.result;

import t4upl.service.model.Result.TemporalUnitName;
import t4upl.service.result.temporalunitid.GroupByTemporalUnitIdService;
import t4upl.service.result.temporalunitid.GroupByWeekServiceImpl;

public class WeekResultService extends ResultService {


  private final GroupByTemporalUnitIdService groupByTemporalUnitIdService = new GroupByWeekServiceImpl();

  @Override
  protected GroupByTemporalUnitIdService getGroupByTemporalUnitIdService() {
    return groupByTemporalUnitIdService;
  }

  @Override
  protected TemporalUnitName getTemporalUnit() {
    return TemporalUnitName.WEEK;
  }


}
