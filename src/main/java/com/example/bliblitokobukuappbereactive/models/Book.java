package com.example.bliblitokobukuappbereactive.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    public static final String COLLECTION_NAME = "books";

    @Id
    private String id;

    @Version
    private int version;

    private String title;
    private String author;
    private int stock;
    private double price;
    private double discount = 1;

    public Book(String title, String author, int stock, double price) {
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
    }

    public Book(String title, String author, int stock, double price, double discount) {
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
    }
}
