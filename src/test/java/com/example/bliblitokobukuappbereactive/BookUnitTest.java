package com.example.bliblitokobukuappbereactive;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.bliblitokobukuappbereactive.controller.BookController;
import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.model.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.model.dto.embedded.GetBookWebResponse;
import com.example.bliblitokobukuappbereactive.service.impl.BookServiceImpl;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest(classes = BlibliTokoBukuAppBeReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookUnitTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    BookServiceImpl bookServiceImpl;
    @InjectMocks
    BookController bookController;
    private final Faker faker = new Faker();


    @BeforeEach
    public void before() {
        log.info("mulai tes");
       openMocks(this);
    }


    @Test
    public void getAllBookTest() throws ExecutionException, InterruptedException {


        when(bookServiceImpl.getBooks(null, 1, 1))
                .thenReturn(Mono.just(new GetBookWebResponse()));


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
                35000,
                0
        );
        newBook.setId("1");

        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(newBook, bookDTO);

        when(bookServiceImpl.updateBook( newBook.getId(), bookDTO))
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

        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(newBook, bookDTO);

        when(bookServiceImpl.updateBook(newBook.getId(), bookDTO))
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

        when(bookServiceImpl.findBookById( newBook.getId()))
            .thenReturn(Mono.just(newBook));

        webTestClient.get().uri("/gdn-bookstore-api/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(newBook.getTitle())
        ;

        verify(bookServiceImpl).findBookById("1");
    }
}
