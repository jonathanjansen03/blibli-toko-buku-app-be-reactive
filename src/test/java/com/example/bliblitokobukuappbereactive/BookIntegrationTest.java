package com.example.bliblitokobukuappbereactive;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.bliblitokobukuappbereactive.model.Book;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest(classes = BlibliTokoBukuAppBeReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(BookUnitTest.class);
    private final Faker faker = new Faker();
    private Book book;

    @BeforeEach
    public void setup(){
        logger.info("Commence The Test !");
        book = createSingleDummyBook();
        bookRepository.save(book).subscribe();
    }

    @AfterEach
    public void finisher() {
        logger.info("The Test Is Over !");
        bookRepository.deleteAll().subscribe();
    }

    private Book createSingleDummyBook() {
        Random random = new Random();

        return new Book(
                faker.company().name(),
                faker.name().fullName(),
                random.nextInt(100000),
                random.nextInt(100000)
        );
    }

    @Test
    public void getAllBookTest() {

        webTestClient.get().uri("/gdn-bookstore-api/books/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .contains(book)
        ;
    }
    @Test
    public void insertSingleBookTest() {

        webTestClient.post().uri("/gdn-bookstore-api/books/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(book.getTitle())
                .jsonPath("$.author").isEqualTo(book.getAuthor())
                .jsonPath("$.stock").isEqualTo(book.getStock())
                .jsonPath("$.price").isEqualTo(book.getPrice())
        ;
    }

    @Test
    public void updateSingleBook() {

        book = (Book) bookRepository.findAll()
                .collectList()
                .map(books -> books.stream().findFirst()).subscribe();

        book.setStock(0);
        book.setTitle("Changing");

        webTestClient.put().uri("/gdn-bookstore-api/books/update/{bookId}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo(book.getTitle())
                .jsonPath("$.author").isEqualTo(book.getAuthor())
                .jsonPath("$.stock").isEqualTo(book.getStock())
                .jsonPath("$.price").isEqualTo(book.getPrice())
        ;
    }

    @Test
    public void deleteSingleBook() {
        Book book = createSingleDummyBook();

        bookRepository.save(book).subscribe();

        book = (Book) bookRepository.findAll()
                .collectList()
                .map(books -> books.stream().findFirst()).subscribe();

        webTestClient.delete().uri("/gdn-bookstore-api/books/delete/{bookId}", book.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .isEmpty()
        ;
    }

}
