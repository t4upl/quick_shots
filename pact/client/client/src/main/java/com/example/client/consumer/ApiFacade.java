package com.example.client.consumer;

import com.example.client.controller.dto.Country;
import org.springframework.http.ResponseEntity;

public interface ApiFacade {

  ResponseEntity<Country[]> getCountries();

  ResponseEntity<Country> getCountry(Long id);

  void setBaseUrl(String baseUrl);

}
