package com.example.bliblitokobukuappbereactive.controller;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.service.impl.BookServiceImpl;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/gdn-bookstore-api/books")
public class BookController {

    private BookServiceImpl bookServiceImpl;

    @GetMapping()
    public Mono<GetBookWebResponse> getBooks
    (
        @RequestParam(required = false) String title,
        @RequestParam(required = false, defaultValue = "1") long page,
        @RequestParam(required = false, defaultValue = "25") long size
    )
    {
        try {
            return bookServiceImpl.getBooks(title, page, size);
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
        return bookServiceImpl.insertBook(bookDTO);
    }

    @PutMapping
    (
        path = "/update/{bookId}",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Book> updateBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable("bookId") String id)
    {
        return bookServiceImpl.updateBook(id, bookDTO);
    }

    @DeleteMapping(path = "/delete/{bookId}")
    public Mono<Void> deleteBook(@PathVariable("bookId") String id)
    {
        return bookServiceImpl.deleteBook(id);
    }

    @GetMapping(path = "/{bookId}")
    public Mono<Book> findBookById(@PathVariable("bookId") String id) {
        return bookServiceImpl.findBookById(id);
    }
}
