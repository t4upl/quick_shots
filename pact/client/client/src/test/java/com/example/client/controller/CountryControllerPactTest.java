package com.example.client.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.client.consumer.ApiFacade;
import com.example.client.controller.dto.Country;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "countries_provider")
public class CountryControllerPactTest {

  @Autowired
  private ApiFacade apiFacade;

  private static final Map<String, String> HEADERS = new HashMap<>();
  private static final Country FRANCE = new Country("France", "Paris", 123,
    LocalDate.of(1999, 1, 20));
  private static final Date ESTABLISHED_DATE_EXAMPLE = getEstablishedDateExample();

  @BeforeAll
  private static void init() {
    HEADERS.put("Content-Type", "application/json");
  }


  @Pact(consumer = "countries_consumer")
  public RequestResponsePact singleCountryDsl(PactDslWithProvider builder) {
    return builder
      .given("country with ID 1 exists")
      .uponReceiving("get country with ID 1")
      .path("/countries/1")
      .willRespondWith()
      .status(HttpStatus.OK.value())
      .headers(HEADERS)
      .body(
        new PactDslJsonBody()
          .stringType("name", "France")
          .stringType("capital", "Paris")
          .numberType("population", 123)
          .stringMatcher("establishedDate", "\\d{4}-[0-1]\\d-\\d{2}", "1999-01-20")
      )
      .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "singleCountryDsl")
  void testSingleProductDsl(MockServer mockServer) {
    apiFacade.setBaseUrl(mockServer.getUrl());
    ResponseEntity<Country> response = apiFacade.getCountry(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(FRANCE, response.getBody());
  }

  @Pact(consumer = "countries_consumer")
  public RequestResponsePact singleCountryJson(PactDslWithProvider builder) {
    String countryJson = "{\"name\":\"France\",\"capital\":\"Paris\",\"population\":123,"
      + "\"establishedDate\":\"1999-01-20\"}";

    return builder
      .given("country with ID 1 exists")
      .uponReceiving("get country with ID 1")
      .path("/countries/1")
      .willRespondWith()
      .status(HttpStatus.OK.value())
      .headers(HEADERS)
      .body(countryJson)
      .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "singleCountryJson")
  void testSingleProductJson(MockServer mockServer) {
    apiFacade.setBaseUrl(mockServer.getUrl());
    ResponseEntity<Country> response = apiFacade.getCountry(1L);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(FRANCE, response.getBody());
  }

  @Pact(consumer = "countries_consumer")
  public RequestResponsePact countryNotFound(PactDslWithProvider builder) {
    return builder
      .given("country with ID 62 does not exists")
      .uponReceiving("get country with ID 62")
      .path("/countries/62")
      .willRespondWith()
      .status(HttpStatus.NOT_FOUND.value())
      .headers(HEADERS)
      .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "countryNotFound")
  void testCountryNotFound(MockServer mockServer) {
    apiFacade.setBaseUrl(mockServer.getUrl());
    assertThrows(HttpClientErrorException.class, () -> apiFacade.getCountry(62L));
  }


  @Pact(consumer = "countries_consumer")
  public RequestResponsePact countries(PactDslWithProvider builder) {
    return builder
      .given("countries not empty")
      .uponReceiving("get countries request")
      .path("/countries")
      .willRespondWith()
      .status(HttpStatus.OK.value())
      .headers(HEADERS)
      .body(
        addFrance(PactDslJsonArray.arrayEachLike())
//        Note: Replace by 'addFrance' method
//          .stringType("name", "France")
//          .stringType("capital", "Paris")
//          .numberType("population", 123)
//          .stringMatcher("establishedDate", "\\d{4}-[0-1]\\d-\\d{2}", "1999-01-20")
      )
      .toPact();
  }

  private PactDslJsonBody addFrance(PactDslJsonBody pactDslJsonBody) {
    return pactDslJsonBody
      .stringType("name", "France")
      .stringType("capital", "Paris")
      .numberType("population", 123)
      .date("establishedDate", "yyyy-MM-dd", ESTABLISHED_DATE_EXAMPLE);
  }

  @Test
  @PactTestFor(pactMethod = "countries")
  void testCountries(MockServer mockServer) throws IOException {
    //Note: Debug only
    checkMockedResponse(mockServer.getUrl() + "/countries");

    apiFacade.setBaseUrl(mockServer.getUrl());
    ResponseEntity<Country[]> response = apiFacade.getCountries();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertArrayEquals(new Country[]{FRANCE}, response.getBody());
  }

  //Note: debug only
  private void checkMockedResponse(String uri) throws IOException {
    HttpResponse httpResponse = Request.Get(uri).execute()
      .returnResponse();
    String responseString = EntityUtils.toString(httpResponse.getEntity());
  }

  @SneakyThrows
  private static Date getEstablishedDateExample() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return simpleDateFormat.parse("1999-01-20");
  }


}
