package com.example.mongo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Book {

  @Id
  String id;

  String name;

  String author;

  Publisher publisher;

}
