package com.example.cancel.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Robot {

  @Id
  private Long id;

  private String description;



}
