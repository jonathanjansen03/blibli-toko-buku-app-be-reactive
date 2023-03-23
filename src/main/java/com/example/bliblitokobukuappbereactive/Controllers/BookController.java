package com.example.bliblitokobukuappbereactive.Controllers;

import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/gdn-bookstore-api/books")
public class BookController {
    private BookService bookService;
    @GetMapping
    public List<Book> getAllBook() {
        return bookService.getAllBook().collectList().block();
    }
    @PostMapping(
            path = "/insert",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public Mono<Book> insertBook(Book book) {
        return bookService.insertBook(book);
    }
    @PutMapping(
            path = "/update/{bookId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public Mono<Book> updateBook(@RequestBody Book book, @PathVariable("bookId") String id) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping(path = "/delete/{bookId}")
    public Mono<Void> deleteBook(@PathVariable("bookId") String id){
        return bookService.deleteBook(id);
    }

}
