package t4upl.service.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.CurrencyValue;

@Builder
@Data
public class ResultElement {

  Exchange exchange;
  int temporalUnitId;
  double value;
  List<CurrencyValue> currencyValueList;
  double referenceValue;

  public double getDelta(){
    return value - referenceValue;
  }


}
