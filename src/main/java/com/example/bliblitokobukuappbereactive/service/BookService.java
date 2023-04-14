package com.example.bliblitokobukuappbereactive.service;

import java.util.concurrent.ExecutionException;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.model.Book;

import reactor.core.publisher.Mono;

public interface BookService {
	public Mono<GetBookWebResponse> getBooks(String title, long page, long size) throws ExecutionException, InterruptedException;
	public Mono<Book> insertBook(BookDTO bookDTO);

	public Mono<Book> updateBook(final String id, final BookDTO bookDTO);

	public Mono<Void> deleteBook(final String id);

	public Mono<Book> findBookById(final String id);
}
