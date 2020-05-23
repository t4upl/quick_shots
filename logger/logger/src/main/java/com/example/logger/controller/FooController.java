package com.example.logger.controller;

import com.example.logger.LoggerApplication;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class FooController {

  Logger logger = LoggerFactory.getLogger(LoggerApplication.class);

  @GetMapping("/foo")
  public String foo() {
    log.info("MY INFO");
    log.error("MY ERROR");
    logger.error("This should be printed as Consol1");
    return "foo";
  }




}
