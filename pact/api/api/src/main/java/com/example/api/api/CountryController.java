package com.example.api.api;

import com.example.api.api.dto.Country;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import jdk.jfr.ContentType;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

  @Getter
  private Map<Long, Country> countriesMap = new HashMap<>();

  public CountryController() {
    intiCountryList();
  }

  private void intiCountryList() {
    countriesMap.put(1L, new Country("Germany", "Berlin", 1, LocalDate.of(1001, 11, 12)));
  }

  @GetMapping("countries/{id}")
  public ResponseEntity<Country> getCountry(@PathVariable Long id) {
    Country country = countriesMap.get(id);

    if (country == null) {
      return ResponseEntity.notFound()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
    }

    return ResponseEntity.ok(country);
  }

  @GetMapping("countries")
  public ResponseEntity<List<Country>> getCountries() {
    List<Country> countries = countriesMap.entrySet().stream()
      .sorted(Comparator.comparing(Entry::getKey))
      .map(Entry::getValue)
      .collect(Collectors.toList());
    return ResponseEntity.ok(countries);
  }

}
