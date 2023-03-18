package t4upl.service.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Result {

  TemporalUnitName temporalUnitName;
  List<ResultElement> resultElementList;

  public enum TemporalUnitName {
    YEAR, MONTH, DAY_OF_WEEK, WEEK, MONTH_OVER_TWO_YEARS
  }

}
