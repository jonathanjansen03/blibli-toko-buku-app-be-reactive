package com.example.bliblitokobukuappbereactive.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.dto.openlibrary.OpenLibraryResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import com.example.bliblitokobukuappbereactive.service.BookService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private static final String BASE_URL = "https://openlibrary.org/search.json?title=";
    private static final String QUERY_LIMIT = "&limit=25";


    private BookRepository bookRepository;
    private ReactiveMongoTemplate reactiveMongoTemplate;
    private WebClient webClient;


    public Flux<Book> consumeAndSave(String title) {
        title = title.replace(" ", "+").toLowerCase();

        Mono<OpenLibraryResponse> responseMono = webClient
                .get()
                .uri(BASE_URL + title + QUERY_LIMIT)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK))
                        return response.bodyToMono(OpenLibraryResponse.class);
                    else if (response.statusCode().is4xxClientError())
                        return Mono.empty();
                    else
                        return response.createException().flatMap(Mono::error);
                });

        return responseMono
                .map(response ->
                        response
                                .getDocs()
                                .stream()
                                .map(Book::build)
                                .collect(Collectors.toList())
                )
                .flatMapMany(books -> bookRepository.saveAll(books));
    }

    @Override
    public Mono<GetBookWebResponse> getBooks(String title, long page, long size) throws ExecutionException, InterruptedException {

        Flux<Book> bookFlux;

        if(title != null && title.trim().length() > 0)
        {
            Query query = new Query()
                                .addCriteria(Criteria.where("title")
                                .regex("\\s*" + title, "i"));

            bookFlux =  reactiveMongoTemplate
                    .find(query, Book.class, "books")
                    .switchIfEmpty(consumeAndSave(title));
        }
        else {
            bookFlux = bookRepository.findAll();
        }

        long documentCount = bookFlux.count().toFuture().get();

        List<Book> bookList = bookFlux
                .skip((page-1) * size)
                .take(size)
                .collectList()
                .toFuture()
                .get();

        return Mono.just(new GetBookWebResponse(documentCount, bookList));
    }

    @Override
    public Mono<Book> insertBook(BookDTO bookDTO)
    {
        return bookRepository.save(Book.build(bookDTO));
    }

    @Override
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

    @Override
    public Mono<Void> deleteBook(final String id)
    {
        return bookRepository.deleteById(id);
    }

    @Override
    public Mono<Book> findBookById(final String id) {
        return bookRepository.findById(id);
    }
}
