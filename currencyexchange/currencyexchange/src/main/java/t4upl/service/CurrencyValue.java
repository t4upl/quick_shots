package t4upl.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CurrencyValue {

  @Setter
  private LocalDate day;
  private Currency currencyFrom;
  private Currency currencyTo;
  private Double value;

  public CurrencyValue(LocalDate day, Currency currencyFrom, Currency currencyTo, Double value) {
    this.day = day;
    this.currencyFrom = currencyFrom;
    this.currencyTo = currencyTo;
    this.value = value;
  }

  public static LocalDate toDay(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter);
  }

  public CurrencyValue getCopy() {
    return new CurrencyValue(day, currencyFrom, currencyTo, value);
  }

  public int getYear() {
    return day.getYear();
  }

}
