package com.example.bliblitokobukuappbereactive;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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
public class BookCRUDTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

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
                .expectBodyList(Book.class)
        ;
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

        when(bookController.updateBook( newBook, newBook.getId()))
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

        when(bookController.updateBook( newBook, newBook.getId()))
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
    public void findBookTest() throws ExecutionException, InterruptedException {
//        when(bookController.findBookById("641827cf1e728026597593c6"))
//                .thenReturn(Mono.just(new Book()));

        Book findBook = bookController.findBookById("641827cf1e728026597593c6").toFuture().get();
    }
}
