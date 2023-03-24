package com.example.bliblitokobukuappbereactive;

import com.example.bliblitokobukuappbereactive.controllers.BookController;
import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.repositories.BookRepository;
import com.example.bliblitokobukuappbereactive.services.BookService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest(classes = BlibliTokoBukuAppBeReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookCRUDTest {
    @Autowired
    private WebTestClient webTestClient;
    @Mock
    BookService bookService;
    @InjectMocks
    BookController bookController;
    private Logger logger = LoggerFactory.getLogger(BookCRUDTest.class);
    private Faker faker = new Faker();

//    @BeforeEach

    @Test
    public void getAllBookTest() {
//        List<Book> books = bookController.getAllBook();
//        for (Book book : books) {
//            logger.info(book.getTitle());
//        }
        when(bookService.getAllBook())
                .thenReturn(Flux.fromIterable(Collections.emptyList()));


        webTestClient.get().uri("/gdn-bookstore-api/books/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class);
    }
    @Test
    public void insertBookTest() {
        Random random = new Random();
        Book newBook = new Book(
                faker.company().name(),
                faker.name().fullName(),
                random.nextInt(100000),
                random.nextInt(100000)
        );

//        bookController.insertBook(newBook).block();

        webTestClient.post().uri("/gdn-bookstore-api/books/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(newBook.getTitle())
        ;
    }

//    @Test
//    public void updateBookTest() {
//        List<Book> books = bookController.getAllBook();
//        Book book = books.get(0);
//        bookService.updateBook(book.getId(),book).subscribe();
//    }
//
//    @Test
//    public void deleteBookTest() {
//        List<Book> books = bookController.getAllBook();
//        Book book = books.get(0);
//        bookService.deleteBook(book.getId()).subscribe();
//    }
}
