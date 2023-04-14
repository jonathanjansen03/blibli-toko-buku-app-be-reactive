package com.example.bliblitokobukuappbereactive.controller;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.service.impl.BookServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/gdn-bookstore-api/books")
@Slf4j
public class BookController {

  private BookServiceImpl bookServiceImpl;

  @GetMapping()
  public Mono<GetBookWebResponse> getBooks(@RequestParam(required = false) String title,
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "25") int size) {
    log.info("#updateBook with book request...");
    try {
      return bookServiceImpl.getBooks(title, page, size);
    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Error : " + e);
      return Mono.empty();
    }
  }

  @PostMapping(path = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public Mono<Book> insertBook(@Valid @RequestBody BookDTO bookDTO) {
    log.info("#insertBook with book request: {}", bookDTO);
    try {
      return bookServiceImpl.insertBook(bookDTO);
    } catch (Exception e) {
      log.error("#insertBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }

  @PutMapping(path = "/update/{bookId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public Mono<Book> updateBook(@Valid @RequestBody BookDTO bookDTO,
      @PathVariable("bookId") String id) {
    log.info("#updateBook with book request: {}", bookDTO);
    try {
      return bookServiceImpl.updateBook(id, bookDTO);
    } catch (Exception e) {
      log.error("#updateBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }

  @DeleteMapping(path = "/delete/{bookId}")
  public Mono<Void> deleteBook(@PathVariable("bookId") String id) {
    log.info("#deleteBook with book id: {}", id);
    try {
      return bookServiceImpl.deleteBook(id);
    } catch (Exception e) {
      log.error("#deleteBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }

  }

  @GetMapping(path = "/{bookId}")
  public Mono<Book> findBookById(@PathVariable("bookId") String id) {
    log.info("#findSingleBook with book id: {}", id);
    try {
      return bookServiceImpl.findBookById(id);
    } catch (Exception e) {
      log.error("#findSingleBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }
}
