package com.example.bliblitokobukuappbereactive;

import com.example.bliblitokobukuappbereactive.controllers.BookController;
import com.example.bliblitokobukuappbereactive.models.Book;
import com.example.bliblitokobukuappbereactive.services.BookService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Random;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest(classes = BlibliTokoBukuAppBeReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookCRUDTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    BookService bookService;

    @Autowired
    BookController bookController;

    private Logger logger = LoggerFactory.getLogger(BookCRUDTest.class);
    private Faker faker = new Faker();

    @Test
    public void getAllBookTest() {
        List<Book> books = bookController.getAllBook();
        for (Book book : books) {
            logger.info(book.getTitle());
        }
    }
    @Test
    public void insertBookTest() {
        Book newBook = new Book();
        Random random = new Random();

        newBook.setTitle(faker.company().name());
        newBook.setAuthor(faker.name().toString());
        newBook.setStock(random.nextInt(100000));
        newBook.setPrice(random.nextInt(100000));
        logger.info(newBook.getTitle());

        bookService.insertBook(newBook).subscribe();

//        webTestClient.post().uri("http://localhost:3000/insert").contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(newBook), Book.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Book.class).isEqualTo(newBook);
    }

    @Test
    public void updateBookTest() {
        List<Book> books = bookController.getAllBook();
        Book book = books.get(0);
        bookService.updateBook(book.getId(),book).subscribe();
    }

    @Test
    public void deleteBookTest() {
        List<Book> books = bookController.getAllBook();
        Book book = books.get(0);
        bookService.deleteBook(book.getId()).subscribe();
    }
}
