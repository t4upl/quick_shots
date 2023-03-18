package t4upl;

import t4upl.service.print.ConsoleWeekPrinter;
import t4upl.service.print.FileYearPrinter;
import t4upl.service.result.DayOfWeekResultService;
import t4upl.service.result.GroupByMonthOverTwoYearResultService;
import t4upl.service.result.MonthlyResultService;
import t4upl.service.loaddata.LoadCurrenciesService;
import t4upl.service.model.Result;
import t4upl.service.print.ConsolePrinter;
import t4upl.service.result.WeekResultService;

/**
 * Hello world!
 */
public class App {

  private static Result monthlyResult;
  private static Result dayOfWeekResult;
  private static Result weekResult;
  private static LoadCurrenciesService loadCurrenciesService;
  private static MonthlyResultService monthlyResultService;
  private static DayOfWeekResultService dayOfWeekResultService;
  private static GroupByMonthOverTwoYearResultService groupByMonthOverTwoYearResultService;
  private static WeekResultService weekResultService;
  private static ConsolePrinter consolePrinter;
  private static ConsoleWeekPrinter consoleWeekPrinter;
  private static FileYearPrinter fileYearPrinter;
  private static Result groupByMonthOverTwoYearsResult;

  public static void main(String[] args) {
    System.out.println("--------------\nSTART");
    initializeServices();
    Result exchangeRates = loadExchangesFromFiles();
    printToFileYearlyResults(exchangeRates);
    getResults(exchangeRates);
    printResults();
    System.out.println("--------------\nEND");
  }

  private static Result loadExchangesFromFiles() {
    return loadCurrenciesService.load();
  }

  private static void printToFileYearlyResults(Result exchangeRates) {
    fileYearPrinter.print(exchangeRates);
  }

  private static void initializeServices() {
    loadCurrenciesService = new LoadCurrenciesService();
    monthlyResultService = new MonthlyResultService();
    dayOfWeekResultService = new DayOfWeekResultService();
    weekResultService = new WeekResultService();
    groupByMonthOverTwoYearResultService = new GroupByMonthOverTwoYearResultService();
    consolePrinter = new ConsolePrinter();
    consoleWeekPrinter = new ConsoleWeekPrinter();
    fileYearPrinter = new FileYearPrinter();
  }

  private static void getResults(Result exchangeRates) {
    monthlyResult = monthlyResultService.calculateResult(exchangeRates);
    dayOfWeekResult = dayOfWeekResultService.calculateResult(exchangeRates);
    weekResult = weekResultService.calculateResult(exchangeRates);
    groupByMonthOverTwoYearsResult = groupByMonthOverTwoYearResultService.calculateResult(exchangeRates);
  }

  private static void printResults() {
    consolePrinter.print(monthlyResult, "monthlyResult");
    consolePrinter.print(dayOfWeekResult, "dayOfWeekResult");
    consoleWeekPrinter.print(weekResult, "weekResult");
    consolePrinter.print(groupByMonthOverTwoYearsResult, "groupByMonthOverTwoYears");
  }

}
