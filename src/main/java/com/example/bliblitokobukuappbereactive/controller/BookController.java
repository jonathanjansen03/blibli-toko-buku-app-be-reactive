package com.example.bliblitokobukuappbereactive.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.model.response.NormalResponse;
import com.example.bliblitokobukuappbereactive.service.BookService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/gdn-bookstore-api/books")
@Slf4j
public class BookController {

  private BookService bookService;

  @ApiOperation("Get Books With/out filter")
  @GetMapping()
  @Cacheable(value = "find-all-books", key = "#title + '-' + #page + '-' + #size")
  public Mono<NormalResponse<List<Book>>> getBooks(@RequestParam(required = false) String title,
                                                   @RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "25") int size) {
    log.info("#getBook with book request...");
    try {
      return bookService.getBooks(title, page, size);
    } catch (ExecutionException | InterruptedException e) {
      log.error("Error : " + e);
      return Mono.empty();
    }
  }

  @ApiOperation("Insert Book")
  @PostMapping(path = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @CacheEvict(value = "find-all-books", allEntries = true)
  public Mono<NormalResponse<Book>> insertBook(@Valid @RequestBody BookDTO bookDTO) {
    log.info("#insertBook with book request: {}", bookDTO);
    try {
      Mono<Book> bookMono = bookService.insertBook(bookDTO);
      return getResponseMono(bookMono);
    } catch (Exception e) {
      log.error("#insertBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }

  @ApiOperation("Update Book")
  @PutMapping(path = "/update/{bookId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @Caching(evict = {@CacheEvict(value = "find-all-books", allEntries = true)},
      put = {@CachePut(value = "find-by-id", key = "#id")})
  public Mono<NormalResponse<Book>> updateBook(@Valid @RequestBody BookDTO bookDTO,
                                               @PathVariable("bookId") String id) {
    log.info("#updateBook with book request: {}", bookDTO);
    try {
      Mono<Book> bookMono = bookService.updateBook(id, bookDTO);
      return getResponseMono(bookMono);
    } catch (Exception e) {
      log.error("#updateBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }

  @ApiOperation("Delete Book")
  @DeleteMapping(path = "/delete/{bookId}")
  @Caching(evict = {@CacheEvict(value = "find-all-books", allEntries = true),
      @CacheEvict(value = "find-by-id", key = "#id")})
  public Mono<NormalResponse<Void>> deleteBook(@PathVariable("bookId") String id) {
    log.info("#deleteBook with book id: {}", id);
    try {
      bookService.deleteBook(id);
      Map<String, String> messageContent = new HashMap<>();
      messageContent.put("Success", "True");
      NormalResponse<Void> response = NormalResponse.<Void>builder().status(HttpStatus.OK.value()).data(null)
          .message(messageContent).build();
      return Mono.just(response);
    } catch (Exception e) {
      log.error("#deleteBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }

  @ApiOperation("Find Single Book By Id")
  @GetMapping(path = "/{bookId}")
  @Cacheable(value = "find-by-id", key = "#id")
  public Mono<NormalResponse<Book>> findBookById(@PathVariable("bookId") String id) {
    log.info("#findSingleBook with book id: {}", id);
    try {
      Mono<Book> bookMono = bookService.findBookById(id);
      return getResponseMono(bookMono);
    } catch (Exception e) {
      log.error("#findSingleBook ERROR! errorMessage: {}", e.getMessage(), e);
      return Mono.empty();
    }
  }

  @NotNull
  private Mono<NormalResponse<Book>> getResponseMono(Mono<Book> bookMono)
      throws ExecutionException, InterruptedException {
    Book bookData = monoToBookConverter(bookMono);

    Map<String, String> messageContent = new HashMap<>();
    messageContent.put("Success", "True");
    NormalResponse<Book> bookResponse = NormalResponse.<Book>builder().status(HttpStatus.OK.value())
        .data(bookData).message(messageContent).count(1).build();
    return Mono.just(bookResponse);
  }

  private Book monoToBookConverter(Mono<Book> bookMono)
      throws ExecutionException, InterruptedException {
    return bookMono.toFuture().get();
  }
}
