package t4upl.service.result;

import t4upl.service.model.Result.TemporalUnitName;
import t4upl.service.result.temporalunitid.GroupByMonthOverTwoYearServiceImpl;
import t4upl.service.result.temporalunitid.GroupByTemporalUnitIdService;

public class GroupByMonthOverTwoYearResultService extends ResultService {


  private final GroupByTemporalUnitIdService groupByTemporalUnitIdService = new GroupByMonthOverTwoYearServiceImpl();

  @Override
  protected GroupByTemporalUnitIdService getGroupByTemporalUnitIdService() {
    return groupByTemporalUnitIdService;
  }

  @Override
  protected TemporalUnitName getTemporalUnit() {
    return TemporalUnitName.MONTH_OVER_TWO_YEARS;
  }


}
