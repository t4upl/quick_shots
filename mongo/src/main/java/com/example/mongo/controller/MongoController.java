package com.example.mongo.controller;

import com.example.mongo.model.Book;
import com.example.mongo.model.BookRepository;
import com.example.mongo.model.Publisher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MongoController {

  private final BookRepository bookRepository;


  @GetMapping("/test")
  String getTest() {
    return "test";
  }

  @GetMapping("/books")
  List<Book> getBooks() {
    return bookRepository.findAll();
  }

  @GetMapping("/new_book")
  Book newBook() {
    Book book1 = new Book();
    book1.setAuthor("book1 author");

    Publisher publisher = new Publisher();
//    publisher.setName("publisher name");
    book1.setPublisher(null);
    Book book = bookRepository.save(book1);
    return book;
  }




}
