package com.example.bliblitokobukuappbereactive.controller;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    {
        try {
            return bookService.getBooks(title, page, size);
        }
        catch (ExecutionException | InterruptedException e) {
            System.out.println("Error : " + e);
            return Mono.empty();
        }
    }

    @PostMapping
    (
        path = "/insert",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Book> insertBook(@Valid @RequestBody BookDTO bookDTO)
    {
        return bookService.insertBook(bookDTO);
    }

    @PutMapping
    (
        path = "/update/{bookId}",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Book> updateBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable("bookId") String id)
    {
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping(path = "/delete/{bookId}")
    public Mono<Void> deleteBook(@PathVariable("bookId") String id)
    {
        return bookService.deleteBook(id);
    }

    @GetMapping(path = "/{bookId}")
    public Mono<Book> findBookById(@PathVariable("bookId") String id) {
        return bookService.findBookById(id);
    }
}
