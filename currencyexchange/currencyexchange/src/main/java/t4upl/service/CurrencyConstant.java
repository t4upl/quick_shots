package t4upl.service;

import java.time.format.DateTimeFormatter;
import java.util.Currency;

public class CurrencyConstant {

  public static final Currency PLN = Currency.getInstance("PLN");
  public static final Currency EUR = Currency.getInstance("EUR");
  public static final Currency USD = Currency.getInstance("USD");

  public static final int YEAR_FROM = 2012;
  public static final int YEAR_TO = 2022;

  public static final String PATH_TO_PRODUCED_FILES = "C:\\Users\\Administrator\\Desktop\\files";

  public static final DateTimeFormatter DAY_WITH_MONTH_FORMATTER = DateTimeFormatter.ofPattern("dd-MM");


  public enum Exchange {
    EUR_TO_PLN_KEY, USD_TO_PLN_KEY
  }

}
