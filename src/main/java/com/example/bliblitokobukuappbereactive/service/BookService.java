package com.example.bliblitokobukuappbereactive.service;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.openlibrary.OpenLibraryResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Service
public class BookService {

    private static final String BASE_URL = "https://openlibrary.org/search.json?title=";
    private static final String QUERY_LIMIT = "&limit=25";


    private BookRepository bookRepository;
    private ReactiveMongoTemplate reactiveMongoTemplate;
    private WebClient webClient;


    public Flux<Book> consumeAndSave(String title)
    {
        title = title.replace(" ", "+").toLowerCase();

        Mono<OpenLibraryResponse> responseMono = webClient
                .get()
                .uri(BASE_URL + title + QUERY_LIMIT)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK))
                        return response.bodyToMono(OpenLibraryResponse.class);
                    else if(response.statusCode().is4xxClientError())
                        return Mono.empty();
                    else
                       return response.createException().flatMap(Mono::error);
                });

        return responseMono
                .map( response ->
                        response
                        .getDocs()
                        .stream()
                        .map(Book::build)
                        .collect(Collectors.toList())
                )
                .flatMapMany(books -> bookRepository.saveAll(books));
    }

    public Flux<Book> getBooks(String title)
    {
        if(title != null && title.trim().length() > 0)
        {
            Query query = new Query()
                                .addCriteria(Criteria.where("title")
                                .regex("\\s*" + title, "i"));

            return reactiveMongoTemplate
                    .find(query, Book.class, "books")
                    .switchIfEmpty(consumeAndSave(title));
        }

        return bookRepository.findAll();
    }

    public Mono<Book> insertBook(BookDTO bookDTO)
    {
        return bookRepository.save(Book.build(bookDTO));
    }

    public Mono<Book> updateBook(final String id, final BookDTO bookDTO)
    {
        return bookRepository
                .findById(id)
                .flatMap(foundBook -> {
                    foundBook.setTitle(bookDTO.getTitle());
                    foundBook.setAuthor(bookDTO.getAuthor());
                    foundBook.setStock(bookDTO.getStock());
                    foundBook.setPrice(bookDTO.getPrice());
                    foundBook.setDiscount(bookDTO.getDiscount());
                    return bookRepository.save(foundBook);
                })
                .switchIfEmpty(
                    Mono.error(
                        new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Book was not found"
                        )
                    )
                );
    }

    public Mono<Void> deleteBook(final String id)
    {
        return bookRepository.deleteById(id);
    }

    public Mono<Book> findBookById(final String id) {
        return bookRepository.findById(id);
    }
}
