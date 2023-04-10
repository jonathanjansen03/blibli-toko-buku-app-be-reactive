package com.example.bliblitokobukuappbereactive.model;

import com.example.bliblitokobukuappbereactive.dto.BookDTO;
import com.example.bliblitokobukuappbereactive.model.metadata.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "books")
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
}
