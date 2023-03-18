package t4upl.service.print;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import t4upl.service.CurrencyConstant.Exchange;
import t4upl.service.model.ResultElement;

@Getter
@AllArgsConstructor
class PrintData {

  Exchange exchange;
  List<ResultElement> resultElementList;
}
