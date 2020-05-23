package com.example.cancel.controller;

import com.example.cancel.component.RobotDao;
import com.example.cancel.data.Robot;
import com.example.cancel.data.RobotRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooCotnroller {

  private final RobotDao robotDao;

  public FooCotnroller(RobotDao robotDao) {
    this.robotDao = robotDao;
  }

  @GetMapping("/run")
  public String run() {
    robotDao.run();
    return "run finished";
  }


  @GetMapping("/cancel")
  public String cancel() {
    robotDao.cancel();
    return "run finished";
  }


  @GetMapping("/query")
  public String query() {
    robotDao.query();
    return "run finished";
  }



}
