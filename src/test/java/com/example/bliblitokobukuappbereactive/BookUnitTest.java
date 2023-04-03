package com.example.bliblitokobukuappbereactive;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Collections;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.bliblitokobukuappbereactive.controllers.BookController;
import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.repositories.BookRepository;
import com.example.bliblitokobukuappbereactive.services.BookService;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest(classes = BlibliTokoBukuAppBeReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookUnitTest {
    @Autowired
    private WebTestClient webTestClient;

    @Mock
    BookService bookService;
    @InjectMocks
    BookController bookController;
    private Logger logger = LoggerFactory.getLogger(BookUnitTest.class);
    private Faker faker = new Faker();


    @BeforeEach
    public void before() {
       openMocks(this);
    }


    @Test
    public void getAllBookTest() throws  InterruptedException {

        when(bookService.getBooks(null))
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

    @Test
    public void updateBookTest() {
        Random random = new Random();
        Book newBook = new Book(
                faker.company().name(),
                faker.name().fullName(),
                random.nextInt(100000),
                random.nextInt(100000)
        );
        newBook.setId("1");

        when(bookService.updateBook( newBook.getId(), newBook))
                .thenReturn(Mono.just(newBook));

        webTestClient.put().uri("/gdn-bookstore-api/books/update/{bookId}", newBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(newBook.getTitle())
        ;
    }

    @Test
    public void deleteBookTest() {
        Random random = new Random();
        Book newBook = new Book(
                faker.company().name(),
                faker.name().fullName(),
                random.nextInt(100000),
                random.nextInt(100000)
        );
        newBook.setId("1");

        when(bookService.updateBook(newBook.getId(), newBook))
                .thenReturn(Mono.just(newBook));

        webTestClient.delete().uri("/gdn-bookstore-api/books/delete/{bookId}", newBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .isEmpty()
        ;
    }


    @Test
    public void findBookTest() {
        Random random = new Random();
        Book newBook = new Book(
                faker.company().name(),
                faker.name().fullName(),
                random.nextInt(100000),
                random.nextInt(100000)
        );
        newBook.setId("1");

        when(bookService.findBookById( anyString()))
            .thenReturn(Mono.just(newBook));

        webTestClient.get().uri("/gdn-bookstore-api/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(newBook.getTitle())
        ;
    }


    @Test
    public void findBookByTitle() throws  InterruptedException {
        String title = "";
        when(bookController.getBooks(title, 1, 25))
                .thenReturn(bookController.getBooks(title, 1, 25));
    }
}
