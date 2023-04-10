package com.example.bliblitokobukuappbereactive.service;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.dto.openlibrary.OpenLibraryBook;
import com.example.bliblitokobukuappbereactive.dto.openlibrary.OpenLibraryResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Service
public class BookService {

    private static final String BASE_URL = "https://openlibrary.org/search.json?title=";
    private static final String QUERY_LIMIT = "&limit=20";

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
                    {
                        return response.bodyToMono(OpenLibraryResponse.class);
                    }
                    else if(response.statusCode().is4xxClientError())
                    {
                        return Mono.empty();
                    }
                    else
                    {
                       return response.createException().flatMap(Mono::error);
                    }
                });

        return responseMono
                .map( response -> response
                                    .getDocs()
                                    .stream()
                                    .map(this::convertDocToBook)
                                    .collect(Collectors.toList())
                )
                .flatMapMany(books -> bookRepository.saveAll(books));
    }

    public Book convertDocToBook(@NotNull OpenLibraryBook openLibraryBook)
    {
        String newBookTitle = openLibraryBook.getTitle();
        String newBookAuthor = "Unknown";

        if(openLibraryBook.getAuthor_name() != null && !openLibraryBook.getAuthor_name().isEmpty())
        {
            newBookAuthor = String.join(", ", openLibraryBook.getAuthor_name());
        }

        int newBookStock = new Random().ints(1, 100).findFirst().getAsInt();
        int newBookPrice = new Random().ints(40, 200).findFirst().getAsInt() * 1000;

        return new Book(newBookTitle, newBookAuthor, newBookStock, newBookPrice, 0);
    }

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

    public Mono<Book> insertBook(BookDTO bookDTO)
    {
        return bookRepository.save(Book.build(bookDTO));
    }

    public Mono<Book> updateBook(final String id, final Book book)
    {
        return bookRepository
                .findById(id)
                .map(foundBook -> {
                    foundBook.setTitle(book.getTitle());
                    foundBook.setAuthor(book.getAuthor());
                    foundBook.setStock(book.getStock());
                    foundBook.setPrice(book.getPrice());
                    foundBook.setDiscount(book.getDiscount());
                    return bookRepository.save(foundBook);
                })
                .flatMap(bookMono -> bookMono)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book was not found")));
    }

    public Mono<Void> deleteBook(final String id)
    {
        return bookRepository.deleteById(id);
    }

    public Mono<Book> findBookById(final String id) {
        return bookRepository.findById(id);
    }
}
