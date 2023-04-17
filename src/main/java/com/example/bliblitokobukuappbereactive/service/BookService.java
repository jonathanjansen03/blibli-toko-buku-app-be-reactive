package com.example.bliblitokobukuappbereactive.service;

import java.util.concurrent.ExecutionException;

import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.model.dto.embedded.GetBookWebResponse;

import reactor.core.publisher.Mono;

public interface BookService {
  Mono<GetBookWebResponse> getBooks(String title, int page, int size)
      throws ExecutionException, InterruptedException;

  Mono<Book> insertBook(BookDTO bookDTO);

  Mono<Book> updateBook(final String id, final BookDTO bookDTO);

  Mono<Void> deleteBook(final String id);

  Mono<Book> findBookById(final String id);
}
