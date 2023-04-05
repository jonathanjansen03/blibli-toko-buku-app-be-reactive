package com.example.bliblitokobukuappbereactive.controller;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/gdn-bookstore-api/books")
public class BookController {

    private BookService bookService;

    @GetMapping()
    public Mono<GetBookWebResponse> getBooks
    (
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "1") long page,
            @RequestParam(required = false, defaultValue = "25") long size
    )
            throws ExecutionException, InterruptedException
    {
        Flux<Book> bookFlux = bookService
                            .getBooks(title)
                            .subscribeOn(Schedulers.boundedElastic());

        long documentCount = bookFlux.count().toFuture().get();


        List<Book> bookList = bookFlux
                .skip((page-1) * size)
                .take(size)
                .collectList()
                .toFuture()
                .get();

        return Mono.just(new GetBookWebResponse(documentCount, bookList));
    }

    @PostMapping
    (
        path = "/insert",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Book> insertBook(@RequestBody @Valid BookDTO bookDTO)
    {
        return bookService.insertBook(bookDTO);
    }

    @PutMapping
    (
        path = "/update/{bookId}",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Book> updateBook(@RequestBody Book book, @PathVariable("bookId") String id)
    {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping(path = "/delete/{bookId}")
    public Mono<Void> deleteBook(@PathVariable("bookId") String id)
    {
        return bookService.deleteBook(id);
    }

    @GetMapping(
            path = "/{bookId}"
    )
    public Mono<Book> findBookById(@PathVariable("bookId") String id) {
        return bookService.findBookById(id);
    }
}
