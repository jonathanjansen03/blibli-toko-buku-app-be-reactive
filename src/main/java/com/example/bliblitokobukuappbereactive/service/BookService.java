package com.example.bliblitokobukuappbereactive.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.model.response.Response;

import reactor.core.publisher.Mono;

public interface BookService {
  Mono<Response<List<Book>>> getBooks(String title, int page, int size)
      throws ExecutionException, InterruptedException;

  Mono<Book> insertBook(BookDTO bookDTO);

  Mono<Book> updateBook(final String id, final BookDTO bookDTO);

  Mono<Void> deleteBook(final String id);

  Mono<Book> findBookById(final String id);
}
