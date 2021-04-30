package com.example.client.consumer;

import com.example.client.controller.dto.Country;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiFacadeImpl implements ApiFacade {


  private final RestTemplate restTemplate;

  @Value("${baseUrl}")
  private String baseUrl;

  public ApiFacadeImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<Country[]> getCountries() {
    return restTemplate
      .getForEntity(getCountriesUrl(), Country[].class);
  }

  @Override
  public ResponseEntity<Country> getCountry(Long id) {
    return restTemplate
      .getForEntity(String.format("%s/%d", getCountriesUrl(), id), Country.class);
  }

  @NotNull
  private String getCountriesUrl() {
    return baseUrl + "/countries";
  }

  @Override
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
