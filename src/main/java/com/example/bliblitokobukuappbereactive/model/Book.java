package com.example.bliblitokobukuappbereactive.model;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.dto.openlibrary.OpenLibraryBook;
import com.example.bliblitokobukuappbereactive.model.metadata.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = Book.COLLECTION_NAME)
public class Book extends AuditMetadata {

    public static final String COLLECTION_NAME = "books";

    @Id
    private String id;

    @Version
    private int version;

    private String title;
    private String author;
    private int stock;
    private double price;
    private double discount;

    public Book (String title, String author, int stock, double price){
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
    }

    public Book (String title, String author, int stock, double price, double discount){
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
    }

    public static Book build(BookDTO bookDTO){
        return new Book(
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getStock(),
                bookDTO.getPrice(),
                bookDTO.getDiscount()
        );
    }

    public static Book build(OpenLibraryBook openLibraryBook){
        String newBookTitle = openLibraryBook.getTitle();
        String newBookAuthor = "Unknown";

        if (openLibraryBook.getAuthor_name() != null && !openLibraryBook.getAuthor_name().isEmpty())
        {
            newBookAuthor = String.join(", ", openLibraryBook.getAuthor_name());
        }

        int newBookStock = new Random().ints(1, 100).findFirst().getAsInt();
        int newBookPrice = new Random().ints(35, 200).findFirst().getAsInt() * 1000;

        return new Book(newBookTitle, newBookAuthor, newBookStock, newBookPrice, 0);
    }


}
