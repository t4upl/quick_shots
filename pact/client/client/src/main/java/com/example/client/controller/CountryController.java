package com.example.client.controller;

import com.example.client.consumer.ApiFacadeImpl;
import com.example.client.controller.dto.Country;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

  private final ApiFacadeImpl apiFacade;

  public CountryController(ApiFacadeImpl apiFacade) {
    this.apiFacade = apiFacade;
  }

  @GetMapping("countries/{id}")
  public Country getCountry(@PathVariable Long id) {
    Country country = apiFacade.getCountry(id).getBody();
    if (country == null) {
      throw new RuntimeException("countries is null");
    }
    return country;
  }

  @GetMapping("countries")
  public List<Country> getCountries() {
    Country[] countries = apiFacade.getCountries().getBody();
    if (countries == null) {
      throw new RuntimeException("countries is null");
    }
    return Arrays.asList(countries);
  }

}
