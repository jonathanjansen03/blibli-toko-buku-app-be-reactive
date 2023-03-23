package com.example.bliblitokobukuappbereactive.services;

import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
@Service
public class BookService {
    private BookRepository bookRepository;

    public Flux<Book> getAllBook() {
        return bookRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Book> getBookById(final String id) {
        return bookRepository.findById(id);
    }

    public Mono<Book> updateBook(final String id, final Book book) {
        Mono<Book> findBook = getBookById(id);
        if(findBook == null) {
            return Mono.empty();
        }
        return bookRepository.save(book);
    }

    public Mono<Book> insertBook(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Void> deleteBook(final String id) {
        Mono<Book> findBook = getBookById(id);
        if(findBook == null) {
            return Mono.empty();
        }
        return bookRepository.deleteById(id);
    }
}
